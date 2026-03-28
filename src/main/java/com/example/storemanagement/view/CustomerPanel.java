package com.example.storemanagement.view;

import com.example.storemanagement.controller.CustomerController;
import com.example.storemanagement.model.entity.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtPhone, txtAddress;

    private CustomerController controller = new CustomerController();

    public CustomerPanel() {
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

        form.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        form.add(txtPhone);

        form.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        form.add(txtAddress);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Address"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // khóa edit
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

        // ===== LOAD DATA =====
        loadData();

        // ===== EVENTS =====
        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        btnAdd.addActionListener(e -> {
            controller.addCustomer(
                    txtName.getText(),
                    txtPhone.getText(),
                    txtAddress.getText()
            );
            loadData();
            clear();
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            controller.updateCustomer(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    txtPhone.getText(),
                    txtAddress.getText()
            );
            loadData();
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            controller.deleteCustomer(Integer.parseInt(txtId.getText()));
            loadData();
            clear();
        });

        btnClear.addActionListener(e -> clear());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Customer> list = controller.getAllCustomers();

        for (Customer c : list) {
            model.addRow(new Object[]{
                    c.getCustomerID(),
                    c.getCustomerName(),
                    c.getPhone(),
                    c.getAddress()
            });
        }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtId.setText(model.getValueAt(row, 0).toString());
            txtName.setText(model.getValueAt(row, 1).toString());
            txtPhone.setText(model.getValueAt(row, 2).toString());
            txtAddress.setText(model.getValueAt(row, 3).toString());
        }
    }

    private void clear() {
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        table.clearSelection();
    }
}