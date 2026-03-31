package com.example.storemanagement.view;

import com.example.storemanagement.controller.BranchController;
import com.example.storemanagement.controller.ProductController;
import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Branch;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ProductStockPanel extends JPanel {

    private JComboBox<Object> cbBranch;
    private JTable table;
    private DefaultTableModel model;

    private ProductController productController = new ProductController();
    private BranchController branchController = new BranchController();

    // ===== THEME COLORS (Đã đồng bộ giao diện Xanh dương đậm) =====
    private final Color PANEL = new Color(17, 89, 135);   // Xanh dương đậm
    private final Color PRIMARY = new Color(64, 160, 220); // Xanh dương nhạt làm viền Info
    private final Color TEXT = Color.WHITE;               // Chữ trắng

    public ProductStockPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== TOP PANEL (Bộ lọc) =====
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        top.setBackground(PANEL);

        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY, 2),
                "Product Stock Filter"
        );
        border.setTitleColor(TEXT);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        top.setBorder(border);

        cbBranch = createComboBox();
        cbBranch.setPreferredSize(new Dimension(300, 35));

        JButton btnReload = createRoundedButton("Reload", PRIMARY);
        btnReload.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        top.add(createLabel("Branch:"));
        top.add(cbBranch);
        top.add(btnReload);

        add(top, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"STT", "Product ID", "Product Name", "Price", "Quantity"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = createTable(model);
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setMaxWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // CẬP NHẬT: Viền đen tuyệt đối cho bảng
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(scrollPane, BorderLayout.CENTER);

        // ===== LOAD DATA =====
        loadBranches();
        loadProducts(null);

        // ===== EVENTS =====
        cbBranch.addActionListener(e -> {
            Object selected = cbBranch.getSelectedItem();
            if (selected instanceof Branch) {
                loadProducts(((Branch) selected).getBranchID());
            } else {
                loadProducts(null);
            }
        });

        btnReload.addActionListener(e -> reload());
    }

    private JLabel createLabel(String text) {
        JLabel lb = new JLabel(text);
        lb.setForeground(TEXT);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return lb;
    }

    private JComboBox<Object> createComboBox() {
        JComboBox<Object> cb = new JComboBox<>();
        cb.setBackground(Color.WHITE);
        cb.setForeground(Color.BLACK);
        cb.setFocusable(false);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return cb;
    }

    private JButton createRoundedButton(String text, Color bgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(bgColor.darker());
                else if (getModel().isRollover()) g2.setColor(bgColor.brighter());
                else g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));

        // CẬP NHẬT: Chọn dòng màu xanh lá
        table.setSelectionBackground(new Color(0, 204, 0));
        table.setSelectionForeground(Color.WHITE);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 45));
        header.setResizingAllowed(false);
        header.setReorderingAllowed(false);

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // CẬP NHẬT: Header màu xám, viền đen
                label.setBackground(new Color(102, 102, 102));
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(1, 1, 4, 1, Color.BLACK),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
                return label;
            }
        });

        return table;
    }

    private void loadBranches() {
        cbBranch.removeAllItems();
        cbBranch.addItem("All Branches");
        List<Branch> list = branchController.getAll();
        for (Branch b : list) cbBranch.addItem(b);
    }

    private void loadProducts(Integer branchId) {
        model.setRowCount(0);
        int stt = 1;
        List<ProductStockDTO> list = productController.getProductStockByBranch(branchId);
        for (ProductStockDTO p : list) {
            model.addRow(new Object[]{
                    stt++, p.getProductId(), p.getProductName(),
                    p.getUnitPrice(), p.getTotalQuantity()
            });
        }
    }

    private void reload() {
        Object selected = cbBranch.getSelectedItem();
        if (selected instanceof Branch) loadProducts(((Branch) selected).getBranchID());
        else loadProducts(null);
    }
    public void refreshData() {
        loadBranches();
        reload();
    }
}