package com.example.storemanagement.view;

import com.example.storemanagement.controller.SupplierController;
import com.example.storemanagement.model.entity.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SupplierPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtAddress, txtPhone;

    private SupplierController controller = new SupplierController();

    public SupplierPanel() {
        setLayout(new BorderLayout());

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));

        form.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEnabled(false);
        form.add(txtId);

        form.add(new JLabel("Name:"));
        txtName = new JTextField();
        form.add(txtName);

        form.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        form.add(txtAddress);

        form.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        form.add(txtPhone);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Address", "Phone"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // khóa chỉnh sửa trực tiếp
            }
        };

        table = new JTable(model);
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel buttons = new JPanel();

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);

        add(buttons, BorderLayout.SOUTH);

        loadData();

        // ===== EVENTS =====

        // click row → fill form
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtAddress.setText(model.getValueAt(row, 2).toString());
                txtPhone.setText(model.getValueAt(row, 3).toString());
            }
        });

        // ADD
        btnAdd.addActionListener(e -> {
            controller.addSupplier(
                    txtName.getText(),
                    txtAddress.getText(),
                    txtPhone.getText()
            );
            loadData();
            clear();
        });

        // UPDATE
        btnUpdate.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            int id = Integer.parseInt(txtId.getText());

            controller.updateSupplier(
                    id,
                    txtName.getText(),
                    txtAddress.getText(),
                    txtPhone.getText()
            );
            loadData();
        });

        // DELETE
        btnDelete.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            int id = Integer.parseInt(txtId.getText());
            controller.deleteSupplier(id);

            loadData();
            clear();
        });

        // CLEAR
        btnClear.addActionListener(e -> clear());
    }

    // ===== LOAD DATA =====
    private void loadData() {
        model.setRowCount(0);

        for (Supplier s : controller.getAllSuppliers()) {
            model.addRow(new Object[]{
                    s.getSupplierID(),
                    s.getSupplierName(),
                    s.getAddress(),
                    s.getPhone()
            });
        }
    }

    // ===== CLEAR FORM =====
    private void clear() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        table.clearSelection();
    }
}