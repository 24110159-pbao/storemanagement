package com.example.storemanagement.view;

import com.example.storemanagement.controller.SupplierController;
import com.example.storemanagement.model.entity.Supplier;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class SupplierPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtName, txtAddress, txtPhone;

    private SupplierController controller = new SupplierController();

    // ===== THEME COLORS (Đã đồng bộ hoàn toàn với CategoryPanel xanh dương) =====
    private final Color PANEL = new Color(17, 89, 135);   // Xanh dương đậm
    private final Color PRIMARY = new Color(64, 160, 220); // Xanh dương nhạt (làm viền)
    private final Color TEXT = Color.WHITE;               // Chữ trắng

    public SupplierPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== FORM =====
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(PANEL);

        // Cập nhật viền TitledBorder sang màu PRIMARY (Xanh dương)
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(PRIMARY, 2),
                "Supplier Info"
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
        txtAddress = createTextField();
        txtPhone = createTextField();

        // Layout các ô nhập liệu
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        innerForm.add(createLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1;
        txtId.setPreferredSize(new Dimension(500, 35));
        innerForm.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        innerForm.add(createLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1;
        innerForm.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        innerForm.add(createLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1;
        innerForm.add(txtAddress, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        innerForm.add(createLabel("Phone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1;
        innerForm.add(txtPhone, gbc);

        form.add(innerForm);
        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"STT", "ID", "Name", "Address", "Phone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = createTable(model);
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setMaxWidth(80);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Viền bảng giữ màu Đen theo phong cách mới bạn đã chọn
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

        loadData();

        // ===== EVENTS =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtId.setText(model.getValueAt(row, 1).toString());
                txtName.setText(model.getValueAt(row, 2).toString());
                txtAddress.setText(model.getValueAt(row, 3).toString());
                txtPhone.setText(model.getValueAt(row, 4).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            controller.addSupplier(txtName.getText(), txtAddress.getText(), txtPhone.getText());
            loadData();
            clear();
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            controller.updateSupplier(Integer.parseInt(txtId.getText()), txtName.getText(), txtAddress.getText(), txtPhone.getText());
            loadData();
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            controller.deleteSupplier(Integer.parseInt(txtId.getText()));
            loadData();
            clear();
        });

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

    private JButton createRoundedButton(String text, Color bgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
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

        // Màu nền khi chọn dòng sang Xanh lá (0, 204, 0)
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
                label.setBackground(new Color(102, 102, 102)); // Màu xám tiêu đề
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setHorizontalAlignment(SwingConstants.CENTER);

                // Viền tiêu đề màu Đen đồng bộ
                label.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(1, 1, 4, 1, Color.BLACK),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
                return label;
            }
        });

        return table;
    }

    private void loadData() {
        model.setRowCount(0);
        int stt = 1;
        for (Supplier s : controller.getAllSuppliers()) {
            model.addRow(new Object[]{
                    stt++,
                    s.getSupplierID(),
                    s.getSupplierName(),
                    s.getAddress(),
                    s.getPhone()
            });
        }
    }

    private void clear() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        table.clearSelection();
    }
}