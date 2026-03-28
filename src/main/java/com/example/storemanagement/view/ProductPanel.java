package com.example.storemanagement.view;

import com.example.storemanagement.controller.ProductController;
import com.example.storemanagement.controller.CategoryController;
import com.example.storemanagement.model.entity.Product;
import com.example.storemanagement.model.entity.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtPrice;
    private JComboBox<Category> cbCategory;

    private ProductController controller = new ProductController();
    private CategoryController categoryController = new CategoryController();

    // ✅ Lazy load flag
    private boolean isCategoryLoaded = false;

    public ProductPanel() {
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

        form.add(new JLabel("Price:"));
        txtPrice = new JTextField();
        form.add(txtPrice);

        form.add(new JLabel("Category:"));
        cbCategory = new JComboBox<>();

        // ✅ Lazy load khi click
        cbCategory.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                if (!isCategoryLoaded) {
                    loadCategories();
                    //isCategoryLoaded = true;
                }
            }

            @Override public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {}
            @Override public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
        });

        form.add(cbCategory);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Price", "Category"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
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

        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnClear.addActionListener(e -> clear());
    }

    // ===== LOAD CATEGORY =====
    private void loadCategories() {
        cbCategory.removeAllItems();

        List<Category> list = categoryController.getAll();

        for (Category c : list) {
            cbCategory.addItem(c);
        }

        cbCategory.setSelectedIndex(-1);
    }

    // ===== LOAD PRODUCT =====
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

    // ===== ADD =====
    private void addProduct() {
        try {
            Category c = (Category) cbCategory.getSelectedItem();

            if (c == null) {
                JOptionPane.showMessageDialog(this, "Please select category!");
                return;
            }

            controller.add(
                    txtName.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    c.getCategoryID()
            );

            loadData();
            clear();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    // ===== UPDATE =====
    private void updateProduct() {
        if (txtId.getText().isEmpty()) return;

        try {
            Category c = (Category) cbCategory.getSelectedItem();

            if (c == null) {
                JOptionPane.showMessageDialog(this, "Please select category!");
                return;
            }

            controller.update(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    Double.parseDouble(txtPrice.getText()),
                    c.getCategoryID()
            );

            loadData();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    // ===== DELETE =====
    private void deleteProduct() {
        if (txtId.getText().isEmpty()) return;

        controller.delete(Integer.parseInt(txtId.getText()));
        loadData();
        clear();
    }

    // ===== FILL FORM =====
    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {

            // ✅ đảm bảo category đã load
            if (!isCategoryLoaded) {
                loadCategories();
                isCategoryLoaded = true;
            }

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

    // ===== CLEAR =====
    private void clear() {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        cbCategory.setSelectedIndex(-1);
        table.clearSelection();
    }
}