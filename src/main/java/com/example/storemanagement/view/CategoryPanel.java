package com.example.storemanagement.view;

import com.example.storemanagement.controller.CategoryController;
import com.example.storemanagement.model.entity.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategoryPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtId, txtName;

    private CategoryController controller = new CategoryController();

    public CategoryPanel() {
        setLayout(new BorderLayout());

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(2, 2));
        form.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEnabled(false);
        form.add(txtId);

        form.add(new JLabel("Name:"));
        txtName = new JTextField();
        form.add(txtName);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"ID", "Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // khóa edit
            }
        };
        table = new JTable(model);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
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
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
            }
        });

        btnAdd.addActionListener(e -> {
            controller.add(txtName.getText());
            loadData();
            clear();
        });

        btnUpdate.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            controller.update(id, txtName.getText());
            loadData();
        });

        btnDelete.addActionListener(e -> {
            int id = Integer.parseInt(txtId.getText());
            controller.delete(id);
            loadData();
            clear();
        });

        btnClear.addActionListener(e -> clear());
    }

    private void loadData() {
        model.setRowCount(0);
        for (Category c : controller.getAll()) {
            model.addRow(new Object[]{
                    c.getCategoryID(),
                    c.getCategoryName()
            });
        }
    }

    private void clear() {
        txtId.setText("");
        txtName.setText("");
    }
}
