package com.example.storemanagement.view;

import com.example.storemanagement.controller.*;
import com.example.storemanagement.model.entity.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class BatchPanel extends JPanel {

    private JTable tableBatch, tableProduct;
    private DefaultTableModel modelBatch, modelProduct;

    private JComboBox<Branch> cbBranch;
    private JComboBox<Supplier> cbSupplier;

    private JTextField txtQuantity, txtSearch;

    private BatchController controller = new BatchController();
    private ProductController productController = new ProductController();
    private SupplierController supplierController = new SupplierController();
    private BranchController branchController = new BranchController();

    private Product selectedProduct;

    // ===== THEME COLORS (Đã đồng bộ giao diện Xanh dương đậm) =====
    private final Color PANEL = new Color(17, 89, 135);   // Xanh dương đậm
    private final Color PRIMARY = new Color(64, 160, 220); // Xanh dương nhạt làm viền Info
    private final Color TEXT = Color.WHITE;               // Chữ trắng

    public BatchPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== TOP FORM =====
        JPanel top = new JPanel(new GridBagLayout());
        top.setBackground(PANEL);
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY, 2),
                "Batch Info"
        );
        border.setTitleColor(TEXT);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        top.setBorder(border);

        JPanel innerForm = new JPanel(new GridBagLayout());
        innerForm.setBackground(PANEL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cbBranch = createComboBox();
        cbSupplier = createComboBox();
        txtQuantity = createTextField();

        cbBranch.setPreferredSize(new Dimension(500, 35));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        innerForm.add(createLabel("Branch:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        innerForm.add(cbBranch, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        innerForm.add(createLabel("Supplier:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1;
        innerForm.add(cbSupplier, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        innerForm.add(createLabel("Quantity:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1;
        innerForm.add(txtQuantity, gbc);

        top.add(innerForm);
        add(top, BorderLayout.NORTH);

        // ===== CENTER (JSplitPane) =====
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setDividerLocation(300);
        split.setBorder(null);
        split.setBackground(Color.WHITE);
        split.setEnabled(false);
        split.setDividerSize(5);

        // --- BATCH TABLE (Bảng trên) ---
        modelBatch = new DefaultTableModel(
                new String[]{"STT", "Batch ID", "Product", "Supplier", "Branch", "Date", "Qty"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tableBatch = createTable(modelBatch);
        tableBatch.getColumnModel().getColumn(0).setMaxWidth(50);
        tableBatch.getColumnModel().getColumn(1).setMaxWidth(80);

        JScrollPane scrollBatch = new JScrollPane(tableBatch);
        scrollBatch.getViewport().setBackground(Color.WHITE);
        // Cập nhật viền đen cho bảng
        scrollBatch.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "Imported Batches", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), Color.BLACK));
        split.setTopComponent(scrollBatch);


        // --- PRODUCT TABLE (Bảng dưới) ---
        JPanel productPanel = new JPanel(new BorderLayout(5, 5));
        productPanel.setBackground(Color.WHITE);

        txtSearch = createTextField();
        // Viền cho ô search để không bị "lặn" trong nền trắng
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        JButton btnSearch = createRoundedButton("Search", PRIMARY);
        btnSearch.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        productPanel.add(searchPanel, BorderLayout.NORTH);

        modelProduct = new DefaultTableModel(
                new String[]{"Product ID", "Name", "Price"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tableProduct = createTable(modelProduct);
        tableProduct.getColumnModel().getColumn(0).setMaxWidth(80);

        JScrollPane scrollProduct = new JScrollPane(tableProduct);
        scrollProduct.getViewport().setBackground(Color.WHITE);
        // Cập nhật viền đen cho bảng
        scrollProduct.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "Select Product to Import", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), Color.BLACK));

        productPanel.add(scrollProduct, BorderLayout.CENTER);

        split.setBottomComponent(productPanel);
        add(split, BorderLayout.CENTER);

        // ===== BOTTOM BUTTONS =====
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 15));
        buttons.setBackground(Color.WHITE);

        JButton btnAdd = createRoundedButton("Add Batch", new Color(40, 167, 69));
        JButton btnDelete = createRoundedButton("Delete Batch", new Color(220, 53, 69));

        buttons.add(btnAdd);
        buttons.add(btnDelete);

        add(buttons, BorderLayout.SOUTH);

        loadCombo();
        loadBatch();
        loadProduct();

        // ===== EVENTS =====
        tableProduct.getSelectionModel().addListSelectionListener(e -> {
            int row = tableProduct.getSelectedRow();
            if (row >= 0) {
                int id = Integer.parseInt(modelProduct.getValueAt(row, 0).toString());
                selectedProduct = productController.findById(id);
            }
        });

        btnSearch.addActionListener(e -> searchProduct());

        btnAdd.addActionListener(e -> {
            if (selectedProduct == null) {
                showMsg("Vui lòng chọn một Sản phẩm ở bảng bên dưới!");
                return;
            }
            if (cbSupplier.getSelectedItem() == null) {
                showMsg("Vui lòng chọn Nhà cung cấp!");
                return;
            }
            if (cbBranch.getSelectedItem() == null) {
                showMsg("Vui lòng chọn Chi nhánh!");
                return;
            }
            int quantity;
            try {
                quantity = Integer.parseInt(txtQuantity.getText());
                if (quantity <= 0) {
                    showMsg("Số lượng phải lớn hơn 0!");
                    return;
                }
            } catch (Exception ex) {
                showMsg("Số lượng phải là một số nguyên!");
                return;
            }
            controller.add(selectedProduct, (Supplier) cbSupplier.getSelectedItem(), (Branch) cbBranch.getSelectedItem(), new Date(), quantity);
            showMsg("Nhập hàng thành công!");
            loadBatch();
            txtQuantity.setText("");
            tableProduct.clearSelection();
            selectedProduct = null;
        });

        btnDelete.addActionListener(e -> {
            int row = tableBatch.getSelectedRow();
            if (row >= 0) {
                int id = Integer.parseInt(modelBatch.getValueAt(row, 1).toString());
                controller.delete(id);
                loadBatch();
            } else {
                showMsg("Vui lòng chọn một Lô hàng ở bảng trên để xóa!");
            }
        });
    }

    private JLabel createLabel(String text) {
        JLabel lb = new JLabel(text);
        lb.setForeground(TEXT);
        lb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return lb;
    }

    private JTextField createTextField() {
        JTextField txt = new JTextField();
        txt.setBackground(Color.WHITE);
        txt.setForeground(Color.BLACK);
        txt.setCaretColor(Color.BLACK);
        txt.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        return txt;
    }

    private <T> JComboBox<T> createComboBox() {
        JComboBox<T> cb = new JComboBox<>();
        cb.setBackground(Color.WHITE);
        cb.setForeground(Color.BLACK);
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
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
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

    private void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadCombo() {
        cbSupplier.removeAllItems();
        for (Supplier s : supplierController.getAllSuppliers()) cbSupplier.addItem(s);
        cbSupplier.setSelectedIndex(-1);

        cbBranch.removeAllItems();
        for (Branch b : branchController.getAll()) cbBranch.addItem(b);
        cbBranch.setSelectedIndex(-1);
    }

    private void loadBatch() {
        modelBatch.setRowCount(0);
        int stt = 1;
        List<Batch> list = controller.getAll();
        for (Batch b : list) {
            modelBatch.addRow(new Object[]{
                    stt++, b.getBatchID(),
                    b.getProduct() != null ? b.getProduct().getProductName() : "",
                    b.getSupplier() != null ? b.getSupplier().getSupplierName() : "",
                    b.getBranch() != null ? b.getBranch().getBranchName() : "",
                    b.getImportDate(), b.getQuantity()
            });
        }
    }

    private void loadProduct() {
        modelProduct.setRowCount(0);
        for (Product p : productController.getAll()) {
            modelProduct.addRow(new Object[]{p.getProductID(), p.getProductName(), p.getUnitPrice()});
        }
    }

    private void searchProduct() {
        String keyword = txtSearch.getText().trim();
        modelProduct.setRowCount(0);
        for (Product p : productController.search(keyword)) {
            modelProduct.addRow(new Object[]{p.getProductID(), p.getProductName(), p.getUnitPrice()});
        }
    }
}