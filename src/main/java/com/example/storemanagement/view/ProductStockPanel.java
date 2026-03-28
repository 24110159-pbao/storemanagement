package com.example.storemanagement.view;

import com.example.storemanagement.controller.BranchController;
import com.example.storemanagement.controller.ProductController;
import com.example.storemanagement.model.dto.ProductStockDTO;
import com.example.storemanagement.model.entity.Branch;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductStockPanel extends JPanel {

    private JComboBox<Object> cbBranch;
    private JTable table;
    private DefaultTableModel model;

    private ProductController productController = new ProductController();
    private BranchController branchController = new BranchController();

    public ProductStockPanel() {
        setLayout(new BorderLayout());

        // ===== TOP =====
        JPanel top = new JPanel();

        cbBranch = new JComboBox<>();
        JButton btnReload = new JButton("Reload");

        top.add(new JLabel("Branch:"));
        top.add(cbBranch);
        top.add(btnReload);

        add(top, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Product Name", "Price", "Quantity"}, 0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== LOAD DATA =====
        loadBranches();
        loadProducts(null);

        // ===== EVENTS =====
        cbBranch.addActionListener(e -> {
            Object selected = cbBranch.getSelectedItem();

            if (selected instanceof Branch) {
                Branch b = (Branch) selected;
                loadProducts(b.getBranchID());
            } else {
                loadProducts(null); // ALL
            }
        });

        btnReload.addActionListener(e -> reload());
    }

    private void loadBranches() {
        cbBranch.removeAllItems();
        cbBranch.addItem("All");

        List<Branch> list = branchController.getAll();
        for (Branch b : list) {
            cbBranch.addItem(b);
        }
    }

    private void loadProducts(Integer branchId) {
        model.setRowCount(0);

        List<ProductStockDTO> list =
                productController.getProductStockByBranch(branchId);

        for (ProductStockDTO p : list) {
            model.addRow(new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    p.getUnitPrice(),
                    p.getTotalQuantity()
            });
        }
    }

    private void reload() {
        Object selected = cbBranch.getSelectedItem();

        if (selected instanceof Branch) {
            loadProducts(((Branch) selected).getBranchID());
        } else {
            loadProducts(null);
        }
    }
}