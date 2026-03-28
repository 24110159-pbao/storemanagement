package com.example.storemanagement.view;

import com.example.storemanagement.controller.BatchController;
import com.example.storemanagement.controller.ProductController;
import com.example.storemanagement.controller.SupplierController;
import com.example.storemanagement.controller.BranchController;
import com.example.storemanagement.model.entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    public BatchPanel() {
        setLayout(new BorderLayout());

        // ===== TOP FORM =====
        JPanel top = new JPanel(new GridLayout(3, 2, 5, 5));

        cbBranch = new JComboBox<>();
        cbSupplier = new JComboBox<>();
        txtQuantity = new JTextField();

        top.add(new JLabel("Branch:"));
        top.add(cbBranch);

        top.add(new JLabel("Supplier:"));
        top.add(cbSupplier);

        top.add(new JLabel("Quantity:"));
        top.add(txtQuantity);

        add(top, BorderLayout.NORTH);

        // ===== CENTER SPLIT =====
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        // ===== BATCH TABLE =====
        modelBatch = new DefaultTableModel(
                new String[]{"ID", "Product", "Supplier", "Branch", "Date", "Qty"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableBatch = new JTable(modelBatch);
        tableBatch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableBatch.setRowSelectionAllowed(true);
        tableBatch.setColumnSelectionAllowed(false);

        split.setTopComponent(new JScrollPane(tableBatch));

        // ===== PRODUCT TABLE =====
        JPanel productPanel = new JPanel(new BorderLayout());

        txtSearch = new JTextField();
        JButton btnSearch = new JButton("Search");

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        productPanel.add(searchPanel, BorderLayout.NORTH);

        modelProduct = new DefaultTableModel(
                new String[]{"ID", "Name", "Price"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableProduct = new JTable(modelProduct);
        tableProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableProduct.setRowSelectionAllowed(true);
        tableProduct.setColumnSelectionAllowed(false);

        productPanel.add(new JScrollPane(tableProduct), BorderLayout.CENTER);

        split.setBottomComponent(productPanel);
        split.setDividerLocation(250);

        add(split, BorderLayout.CENTER);

        // ===== BUTTON =====
        JPanel buttons = new JPanel();

        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");

        buttons.add(btnAdd);
        buttons.add(btnDelete);

        add(buttons, BorderLayout.SOUTH);

        // ===== LOAD DATA =====
        loadBatch();
        loadProduct();
        loadCombo();

        // ===== EVENTS =====

        // chọn product
        tableProduct.getSelectionModel().addListSelectionListener(e -> {
            int row = tableProduct.getSelectedRow();
            if (row >= 0) {
                int id = (int) modelProduct.getValueAt(row, 0);
                selectedProduct = productController.findById(id);
            }
        });

        // search
        btnSearch.addActionListener(e -> searchProduct());

        // add
        btnAdd.addActionListener(e -> {

            if (selectedProduct == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!");
                return;
            }

            if (cbSupplier.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!");
                return;
            }

            if (cbBranch.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chi nhánh!");
                return;
            }

            if (txtQuantity.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(txtQuantity.getText());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải > 0!");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số!");
                return;
            }

            controller.add(
                    selectedProduct,
                    (Supplier) cbSupplier.getSelectedItem(),
                    (Branch) cbBranch.getSelectedItem(),
                    new Date(),
                    quantity
            );

            JOptionPane.showMessageDialog(this, "Thêm thành công!");

            loadBatch();

            // reset form
            txtQuantity.setText("");
            tableProduct.clearSelection();
            selectedProduct = null;
        });

        // delete
        btnDelete.addActionListener(e -> {
            int row = tableBatch.getSelectedRow();
            if (row >= 0) {
                int id = (int) modelBatch.getValueAt(row, 0);
                controller.delete(id);
                loadBatch();
            }
        });
    }

    private void loadBatch() {
        modelBatch.setRowCount(0);
        List<Batch> list = controller.getAll();

        for (Batch b : list) {
            modelBatch.addRow(new Object[]{
                    b.getBatchID(),
                    b.getProduct().getProductName(),
                    b.getSupplier().getSupplierName(),
                    b.getBranch().getBranchName(),
                    b.getImportDate(),
                    b.getQuantity()
            });
        }
    }

    private void loadProduct() {
        modelProduct.setRowCount(0);
        List<Product> list = productController.getAll();

        for (Product p : list) {
            modelProduct.addRow(new Object[]{
                    p.getProductID(),
                    p.getProductName(),
                    p.getUnitPrice()
            });
        }
    }

    private void loadCombo() {
        for (Supplier s : supplierController.getAllSuppliers()) {
            cbSupplier.addItem(s);
        }

        for (Branch b : branchController.getAll()) {
            cbBranch.addItem(b);
        }
    }

    private void searchProduct() {
        String keyword = txtSearch.getText();

        modelProduct.setRowCount(0);
        List<Product> list = productController.search(keyword);

        for (Product p : list) {
            modelProduct.addRow(new Object[]{
                    p.getProductID(),
                    p.getProductName(),
                    p.getUnitPrice()
            });
        }
    }
}

