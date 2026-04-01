package com.example.storemanagement.view;

import com.example.storemanagement.controller.CategoryController;
import com.example.storemanagement.controller.ProductController;
import com.example.storemanagement.model.entity.Category;
import com.example.storemanagement.model.entity.Product;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ProductPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtPrice;
    private JComboBox<Category> cbCategory;

    private ProductController controller = new ProductController();
    private CategoryController categoryController = new CategoryController();

    // ===== THEME COLORS (Đã đồng bộ giao diện Xanh dương đậm) =====
    private final Color PANEL = new Color(17, 89, 135);   // Xanh dương đậm
    private final Color PRIMARY = new Color(64, 160, 220); // Xanh dương nhạt làm viền
    private final Color TEXT = Color.WHITE;               // Chữ trắng

    public ProductPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== FORM =====
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(PANEL);

        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY, 2),
                "Product Info"
        );
        border.setTitleColor(TEXT);
        border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
        form.setBorder(border);

        JPanel innerForm = new JPanel(new GridBagLayout());
        innerForm.setBackground(PANEL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = createTextField();
        txtId.setEnabled(false);
        txtName = createTextField();
        txtPrice = createTextField();
        cbCategory = createComboBox();

        txtId.setPreferredSize(new Dimension(500, 35));

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        innerForm.add(createLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        innerForm.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        innerForm.add(createLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1;
        innerForm.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        innerForm.add(createLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1;
        innerForm.add(txtPrice, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        innerForm.add(createLabel("Category:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1;
        innerForm.add(cbCategory, gbc);

        form.add(innerForm);
        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"STT", "ID", "Name", "Price", "Category"}, 0
        ) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = createTable(model);
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setMaxWidth(80);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // CẬP NHẬT: Viền đen tuyệt đối
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(scrollPane, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 70, 15));
        buttons.setBackground(Color.WHITE);

        JButton btnAdd = createRoundedButton("Add", new Color(40, 167, 69));
        JButton btnUpdate = createRoundedButton("Update", new Color(253, 126, 20));
        JButton btnDelete = createRoundedButton("Delete", new Color(220, 53, 69));
        JButton btnClear = createRoundedButton("Clear", new Color(108, 117, 125));

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);

        add(buttons, BorderLayout.SOUTH);

        loadCategories();
        loadData();

        // ===== EVENTS =====
        table.getSelectionModel().addListSelectionListener(e -> fillForm());
        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clear());
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

    private JComboBox<Category> createComboBox() {
        JComboBox<Category> cb = new JComboBox<>();
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

        // CẬP NHẬT: Chọn dòng màu Xanh lá
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

    private void loadCategories() {
        cbCategory.removeAllItems();
        List<Category> list = categoryController.getAll();
        for (Category c : list) cbCategory.addItem(c);
        cbCategory.setSelectedIndex(-1);
    }

    private void loadData() {
        model.setRowCount(0);
        int stt = 1;
        List<Product> list = controller.getAll();
        for (Product p : list) {
            model.addRow(new Object[]{
                    stt++, p.getProductID(), p.getProductName(),
                    p.getUnitPrice(), p.getCategory() != null ? p.getCategory().getCategoryName() : ""
            });
        }
    }

    private void addProduct() {
        try {
            Category c = (Category) cbCategory.getSelectedItem();
            if (c == null) { JOptionPane.showMessageDialog(this, "Please select category!"); return; }
            if (txtPrice.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Price is required!");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(txtPrice.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Price must be a number!");
                return;
            }

            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Price cannot be negative!");
                return;
            }

            controller.add(txtName.getText(), price, c.getCategoryID());
            loadData();
            clear();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid input!"); }
    }

    private void updateProduct() {
        if (txtId.getText().isEmpty()) return;
        try {
            Category c = (Category) cbCategory.getSelectedItem();
            if (c == null) { JOptionPane.showMessageDialog(this, "Please select category!"); return; }
            controller.update(Integer.parseInt(txtId.getText()), txtName.getText(), Double.parseDouble(txtPrice.getText()), c.getCategoryID());
            loadData();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid input!"); }
    }

    private void deleteProduct() {
        if (txtId.getText().isEmpty()) return;
        controller.delete(Integer.parseInt(txtId.getText()));
        loadData();
        clear();
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtId.setText(model.getValueAt(row, 1).toString());
            txtName.setText(model.getValueAt(row, 2).toString());
            txtPrice.setText(model.getValueAt(row, 3).toString());
            String categoryName = model.getValueAt(row, 4).toString();
            for (int i = 0; i < cbCategory.getItemCount(); i++) {
                if (cbCategory.getItemAt(i).getCategoryName().equals(categoryName)) {
                    cbCategory.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void clear() {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        cbCategory.setSelectedIndex(-1);
        table.clearSelection();
    }
    public void refreshData() {
        loadCategories();
        loadData();
    }
}