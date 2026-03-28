package com.example.storemanagement.view;

import com.example.storemanagement.controller.ProductController;
import com.example.storemanagement.dao.impl.CategoryDAOImpl;
import com.example.storemanagement.model.entity.Product;
import com.example.storemanagement.model.entity.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtPrice;
    private JComboBox<Category> cbCategory;

    private ProductController controller = new ProductController();
    private CategoryDAOImpl categoryDAO = new CategoryDAOImpl();

    public ProductPanel() {
        setLayout(new BorderLayout());

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));

        txtId = new JTextField();
        txtId.setEnabled(false);

        txtName = new JTextField();
        txtPrice = new JTextField();

        cbCategory = new JComboBox<>();

        form.add(new JLabel("ID:"));
        form.add(txtId);

        form.add(new JLabel("Name:"));
        form.add(txtName);

        form.add(new JLabel("Price:"));
        form.add(txtPrice);

        form.add(new JLabel("Category:"));
        form.add(cbCategory);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Price", "Category"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
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

        // ===== INIT =====

        loadData();

        // ===== EVENTS =====
        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        btnAdd.addActionListener(e -> {
            controller.add(
                    txtName.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    ((Category) cbCategory.getSelectedItem()).getCategoryID()
            );
            loadData();
            clear();
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            controller.update(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    ((Category) cbCategory.getSelectedItem()).getCategoryID()
            );
            loadData();
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            controller.delete(Integer.parseInt(txtId.getText()));
            loadData();
            clear();
        });

        btnClear.addActionListener(e -> clear());
    }

    private void loadCategories() {
        cbCategory.removeAllItems();
        List<Category> list = categoryDAO.findAll();

        for (Category c : list) {
            cbCategory.addItem(c);
        }
    }

    private void loadData() {
        model.setRowCount(0);
        List<Product> list = controller.getAll();

        for (Product p : list) {
            model.addRow(new Object[]{
                    p.getProductID(),
                    p.getProductName(),
                    p.getUnitPrice(),
                    p.getCategory() != null ? p.getCategory().getCategoryName() : ""
            });
        }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtId.setText(model.getValueAt(row, 0).toString());
            txtName.setText(model.getValueAt(row, 1).toString());
            txtPrice.setText(model.getValueAt(row, 2).toString());

            String categoryName = model.getValueAt(row, 3).toString();

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
        table.clearSelection();
    }
}