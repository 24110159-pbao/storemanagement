package com.example.storemanagement.view;

import com.example.storemanagement.controller.BranchController;
import com.example.storemanagement.model.entity.Branch;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BranchPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtAddress, txtPhone;

    private BranchController controller = new BranchController();

    public BranchPanel() {
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
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON =====
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

        // ===== LOAD =====
        loadData();

        // ===== EVENTS =====
        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        btnAdd.addActionListener(e -> {
            controller.addBranch(
                    txtName.getText(),
                    txtAddress.getText(),
                    txtPhone.getText()
            );
            loadData();
            clear();
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            controller.updateBranch(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtPhone.getText()
            );
            loadData();
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            controller.deleteBranch(Integer.parseInt(txtId.getText()));
            loadData();
            clear();
        });

        btnClear.addActionListener(e -> clear());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Branch> list = controller.getAll();

        for (Branch b : list) {
            model.addRow(new Object[]{
                    b.getBranchID(),
                    b.getBranchName(),
                    b.getAddress(),
                    b.getPhone()
            });
        }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtId.setText(model.getValueAt(row, 0).toString());
            txtName.setText(model.getValueAt(row, 1).toString());
            txtAddress.setText(model.getValueAt(row, 2).toString());
            txtPhone.setText(model.getValueAt(row, 3).toString());
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