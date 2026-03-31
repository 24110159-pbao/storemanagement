package com.example.storemanagement.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JPanel sidebar;

    private JButton[] buttons;
    private JButton currentActiveButton;

    // === AUTO RELOAD =====
    private EmployeePanel employeePanel;
    private ProductPanel productPanel;
    private BatchPanel batchPanel;
    private ProductStockPanel productStockPanel;
    private CreateInvoiecPanel createInvoiecPanel;
    private ReportPanel reportPanel;

    // ===== DARK THEME =====
    private final Color SIDEBAR_BG = new Color(0, 0, 0);
    private final Color SIDEBAR_ITEM = new Color(25, 25, 25);
    private final Color SIDEBAR_HOVER = new Color(50, 50, 50);
    private final Color SIDEBAR_ACTIVE = new Color(153, 0, 0);

    private final Color CONTENT_BG = Color.BLACK;
    private final Color TEXT_LIGHT = Color.WHITE;

    public MainFrame() {
        setTitle("Sales Management System");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        UIManager.put("defaultFont", new Font("Segoe UI Emoji", Font.PLAIN, 14));

        // ===== STYLE SCROLLBAR =====
        UIManager.put("ScrollBar.thumb", new Color(80, 80, 80));
        UIManager.put("ScrollBar.track", SIDEBAR_BG);
        UIManager.put("ScrollBar.width", 8);

        // ===== SIDEBAR =====
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        // ===== BUTTONS =====
        JButton btnCategory = createMenuButton("Category", "📁");
        JButton btnSupplier = createMenuButton("Supplier", "🚚");
        JButton btnCustomer = createMenuButton("Customer", "👤");
        JButton btnBranch = createMenuButton("Branch", "🏢");
        JButton btnEmployee = createMenuButton("Employee", "👨‍💼");
        JButton btnProduct = createMenuButton("Product", "📦");
        JButton btnBatch = createMenuButton("Batch", "🧾");
        JButton btnStock = createMenuButton("Product Stock", "📊");
        JButton btnCreateInvoice = createMenuButton("Create Invoice", "🧮");
        JButton btnStatistics = createMenuButton("Statistics", "📈");

        // ===== ORDER FIXED =====
        buttons = new JButton[]{
                btnCategory,
                btnSupplier,
                btnCustomer,
                btnBranch,
                btnEmployee,
                btnProduct,
                btnBatch,
                btnStock,
                btnCreateInvoice,
                btnStatistics
        };

        for (JButton btn : buttons) {
            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(btn);
        }

        // ===== SCROLL =====
        JScrollPane scrollPane = new JScrollPane(sidebar);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(SIDEBAR_BG);
        scrollPane.setPreferredSize(new Dimension(220, 0));

        add(scrollPane, BorderLayout.WEST);

        // ===== CONTENT =====
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(CONTENT_BG);

        contentPanel.add(new CategoryPanel(), "CATEGORY");
        contentPanel.add(new SupplierPanel(), "SUPPLIER");
        contentPanel.add(new CustomerPanel(), "CUSTOMER");
        contentPanel.add(new BranchPanel(), "BRANCH");

        employeePanel = new EmployeePanel();
        contentPanel.add(employeePanel, "EMPLOYEE");

        productPanel = new ProductPanel();
        contentPanel.add(productPanel, "PRODUCT");

        batchPanel = new BatchPanel();
        contentPanel.add(batchPanel, "BATCH");

        productStockPanel = new ProductStockPanel();
        contentPanel.add(productStockPanel, "STOCK");

        createInvoiecPanel = new CreateInvoiecPanel();
        contentPanel.add(createInvoiecPanel, "CREATE_INVOICE");

        reportPanel = new ReportPanel();
        contentPanel.add(reportPanel, "STATISTICS");

        add(contentPanel, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnCategory.addActionListener(e -> switchPanel("CATEGORY", btnCategory));
        btnSupplier.addActionListener(e -> switchPanel("SUPPLIER", btnSupplier));
        btnCustomer.addActionListener(e -> switchPanel("CUSTOMER", btnCustomer));
        btnBranch.addActionListener(e -> switchPanel("BRANCH", btnBranch));

        btnEmployee.addActionListener(e -> {
            switchPanel("EMPLOYEE", btnEmployee);
            employeePanel.refreshData();
        });

        btnProduct.addActionListener(e -> {
            switchPanel("PRODUCT", btnProduct);
            productPanel.refreshData();
        });

        btnBatch.addActionListener(e -> {
            switchPanel("BATCH", btnBatch);
            batchPanel.refreshData();
        });

        btnStock.addActionListener(e -> {
            switchPanel("STOCK", btnStock);
            productStockPanel.refreshData();
        });

        btnCreateInvoice.addActionListener(e -> {
            switchPanel("CREATE_INVOICE", btnCreateInvoice);
            createInvoiecPanel.refreshData();
        });

        btnStatistics.addActionListener(e -> {
            switchPanel("STATISTICS", btnStatistics);
            reportPanel.refreshData();
        });

        // DEFAULT
        currentActiveButton = btnCategory;
        switchPanel("CATEGORY", btnCategory);
    }

    // ===== BUTTON =====
    private JButton createMenuButton(String text, String icon) {
        JButton btn = new JButton(icon + "  " + text) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                super.paintComponent(g);
            }
        };

        btn.setMaximumSize(new Dimension(200, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setBackground(SIDEBAR_ITEM);
        btn.setForeground(TEXT_LIGHT);

        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        btn.setHorizontalAlignment(SwingConstants.LEFT);

        // HOVER
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (btn != currentActiveButton) {
                    btn.setBackground(SIDEBAR_HOVER);
                }
            }

            public void mouseExited(MouseEvent evt) {
                if (btn != currentActiveButton) {
                    btn.setBackground(SIDEBAR_ITEM);
                }
            }
        });

        return btn;
    }

    // ===== SWITCH PANEL =====
    private void switchPanel(String name, JButton btn) {
        cardLayout.show(contentPanel, name);
        currentActiveButton = btn;
        setActiveButton(btn);
    }

    // ===== ACTIVE BUTTON =====
    private void setActiveButton(JButton activeBtn) {
        for (JButton btn : buttons) {
            btn.setBackground(SIDEBAR_ITEM);
            btn.setForeground(TEXT_LIGHT);
            btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        }

        activeBtn.setBackground(SIDEBAR_ACTIVE);
        activeBtn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
    }
}