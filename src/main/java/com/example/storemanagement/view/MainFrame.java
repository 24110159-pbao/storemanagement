package com.example.storemanagement.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton[] buttons;

    public MainFrame() {
        setTitle("Sales Management System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setBackground(new Color(245, 245, 245));

        // ===== BUTTONS =====
        JButton btnCategory = new JButton("Category");
        JButton btnSupplier = new JButton("Supplier");
        JButton btnCustomer = new JButton("Customer");
        JButton btnEmployee = new JButton("Employee");
        JButton btnProduct = new JButton("Product");
        JButton btnBranch = new JButton("Branch");
        JButton btnBatch = new JButton("Batch");
        JButton btnStock = new JButton("Product Stock");
        JButton btnCreateInvoice = new JButton("Create Invoice");
        JButton btnStatistics = new JButton("Statistics");
        JButton btnOption = new JButton("Option");

        buttons = new JButton[]{
                btnCategory,
                btnSupplier,
                btnCustomer,
                btnBranch,
                btnProduct,
                btnEmployee,
                btnBatch,
                btnStock,
                btnCreateInvoice,
                btnStatistics,
                btnOption
        };

        Dimension btnSize = new Dimension(150, 40);

        for (JButton btn : buttons) {
            btn.setMaximumSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
            btn.setOpaque(true);
            btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(btn);
        }

        add(sidebar, BorderLayout.WEST);

        // ===== CONTENT =====
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new CategoryPanel(), "CATEGORY");
        contentPanel.add(new SupplierPanel(), "SUPPLIER");
        contentPanel.add(new CustomerPanel(), "CUSTOMER");
        contentPanel.add(new EmployeePanel(), "EMPLOYEE");
        contentPanel.add(new ProductPanel(), "PRODUCT");
        contentPanel.add(new BranchPanel(), "BRANCH");
        contentPanel.add(new BatchPanel(), "BATCH");
        contentPanel.add(new ProductStockPanel(), "STOCK");
        contentPanel.add(new JLabel("Create Invoice Panel"), "CREATE_INVOICE");
        contentPanel.add(new JLabel("Statistics Panel"), "STATISTICS");
        contentPanel.add(new JLabel("Option Panel"), "OPTION");

        add(contentPanel, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnCategory.addActionListener(e -> {
            cardLayout.show(contentPanel, "CATEGORY");
            setActiveButton(btnCategory);
        });

        btnSupplier.addActionListener(e -> {
            cardLayout.show(contentPanel, "SUPPLIER");
            setActiveButton(btnSupplier);
        });

        btnCustomer.addActionListener(e -> {
            cardLayout.show(contentPanel, "CUSTOMER");
            setActiveButton(btnCustomer);
        });

        btnEmployee.addActionListener(e -> {
            cardLayout.show(contentPanel, "EMPLOYEE");
            setActiveButton(btnEmployee);
        });

        btnProduct.addActionListener(e -> {
            cardLayout.show(contentPanel, "PRODUCT");
            setActiveButton(btnProduct);
        });

        btnBranch.addActionListener(e -> {
            cardLayout.show(contentPanel, "BRANCH");
            setActiveButton(btnBranch);
        });



        btnBatch.addActionListener(e -> {
            cardLayout.show(contentPanel, "BATCH");
            setActiveButton(btnBatch);
        });

        btnStock.addActionListener(e -> {
            cardLayout.show(contentPanel, "STOCK");
            setActiveButton(btnStock);
        });

        btnCreateInvoice.addActionListener(e -> {
            cardLayout.show(contentPanel, "CREATE_INVOICE");
            setActiveButton(btnCreateInvoice);
        });

        btnStatistics.addActionListener(e -> {
            cardLayout.show(contentPanel, "STATISTICS");
            setActiveButton(btnStatistics);
        });

        btnOption.addActionListener(e -> {
            cardLayout.show(contentPanel, "OPTION");
            setActiveButton(btnOption);
        });

        // set mặc định button đầu tiên sáng
        setActiveButton(btnCategory);
    }

    // ===== METHOD HIGHLIGHT BUTTON =====
    private void setActiveButton(JButton activeBtn) {
        for (JButton btn : buttons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(Color.BLACK);
        }

        activeBtn.setBackground(new Color(100, 149, 237)); // xanh đẹp
        activeBtn.setForeground(Color.WHITE);
    }
}