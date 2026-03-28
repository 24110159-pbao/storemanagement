package com.example.storemanagement.view;

import com.example.storemanagement.model.entity.Category;
import com.example.storemanagement.model.entity.Product;
import com.example.storemanagement.model.entity.Supplier;
import com.example.storemanagement.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductPanel extends JPanel {

    private final ProductService productService;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtName;
    private JTextField txtPrice;
    private JComboBox<Category> cbCategory;
    private JComboBox<Supplier> cbSupplier;
    private JCheckBox chkStatus;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnRefresh;

    private Integer selectedProductId = null;

    public ProductPanel(ProductService productService,
                        List<Category> categories,
                        List<Supplier> suppliers) {
        this.productService = productService;

        setLayout(new BorderLayout());

        initComponents(categories, suppliers);
        loadTable();
        initEvents();
    }

    private void initComponents(List<Category> categories, List<Supplier> suppliers) {

        // ================= TABLE =================
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Price", "Category", "Supplier", "Status"}, 0
        );
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ================= FORM =================
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        txtName = new JTextField();
        txtPrice = new JTextField();

        cbCategory = new JComboBox<>();
        for (Category c : categories) {
            cbCategory.addItem(c);
        }

        cbSupplier = new JComboBox<>();
        for (Supplier s : suppliers) {
            cbSupplier.addItem(s);
        }

        chkStatus = new JCheckBox("Assigned");

        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("Price:"));
        formPanel.add(txtPrice);

        formPanel.add(new JLabel("Category:"));
        formPanel.add(cbCategory);

        formPanel.add(new JLabel("Supplier:"));
        formPanel.add(cbSupplier);

        formPanel.add(new JLabel("Status:"));
        formPanel.add(chkStatus);

        // ================= BUTTONS =================
        JPanel buttonPanel = new JPanel();

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void loadTable() {
        tableModel.setRowCount(0);

        List<Product> products = productService.getAll();
        for (Product p : products) {
            tableModel.addRow(new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    p.getUnitPrice(),
                    p.getCategory() != null ? p.getCategory().getCategoryName() : "",
                    p.getSupplier() != null ? p.getSupplier().getSupplierName() : "",
                    p.getStatus()
            });
        }
    }

    private void initEvents() {

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                selectedProductId = (Integer) tableModel.getValueAt(row, 0);
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtPrice.setText(tableModel.getValueAt(row, 2).toString());
                chkStatus.setSelected((Boolean) tableModel.getValueAt(row, 5));
            }
        });

        btnAdd.addActionListener(e -> {
            try {
                Product product = buildProductFromForm();
                productService.create(product);
                clearForm();
                loadTable();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                if (selectedProductId == null) {
                    showError("Select a product first");
                    return;
                }
                Product product = buildProductFromForm();
                product.setProductId(selectedProductId);
                productService.update(product);
                clearForm();
                loadTable();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                if (selectedProductId == null) {
                    showError("Select a product first");
                    return;
                }
                productService.delete(selectedProductId);
                clearForm();
                loadTable();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        btnRefresh.addActionListener(e -> loadTable());
    }

    private Product buildProductFromForm() {
        Product p = new Product();
        p.setProductName(txtName.getText());
        p.setUnitPrice(new BigDecimal(txtPrice.getText()));
        p.setCategory((Category) cbCategory.getSelectedItem());
        p.setSupplier((Supplier) cbSupplier.getSelectedItem());
        p.setStatus(chkStatus.isSelected());
        return p;
    }

    private void clearForm() {
        txtName.setText("");
        txtPrice.setText("");
        chkStatus.setSelected(false);
        selectedProductId = null;
        table.clearSelection();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}