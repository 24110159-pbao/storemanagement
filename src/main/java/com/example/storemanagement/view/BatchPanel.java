package com.example.storemanagement.view;

import com.example.storemanagement.controller.BatchController;
import com.example.storemanagement.dao.impl.*;
import com.example.storemanagement.model.entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class BatchPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtQuantity;

    private JComboBox<Product> cbProduct;
    private JComboBox<Supplier> cbSupplier;
    private JComboBox<Branch> cbBranch;

    private BatchController controller = new BatchController();

    public BatchPanel() {
        setLayout(new BorderLayout());

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        txtId = new JTextField();
        txtId.setEnabled(false);

        txtQuantity = new JTextField();

        cbProduct = new JComboBox<>();
        cbSupplier = new JComboBox<>();
        cbBranch = new JComboBox<>();

        form.add(new JLabel("ID:"));
        form.add(txtId);

        form.add(new JLabel("Product:"));
        form.add(cbProduct);

        form.add(new JLabel("Supplier:"));
        form.add(cbSupplier);

        form.add(new JLabel("Branch:"));
        form.add(cbBranch);

        form.add(new JLabel("Quantity:"));
        form.add(txtQuantity);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Product", "Supplier", "Date", "Qty"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON =====
        JPanel btns = new JPanel();

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        btns.add(btnAdd);
        btns.add(btnUpdate);
        btns.add(btnDelete);
        btns.add(btnClear);

        add(btns, BorderLayout.SOUTH);

        // ===== LOAD DATA =====
        loadCombo();
        loadData();

        // ===== EVENTS =====
        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        btnAdd.addActionListener(e -> {

            if (cbProduct.getSelectedItem() == null ||
                    cbSupplier.getSelectedItem() == null ||
                    cbBranch.getSelectedItem() == null ||
                    txtQuantity.getText().isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            int qty = Integer.parseInt(txtQuantity.getText());

            controller.add(
                    (Product) cbProduct.getSelectedItem(),
                    (Supplier) cbSupplier.getSelectedItem(),
                    (Branch) cbBranch.getSelectedItem(), // 👉 dùng riêng cho branch_product
                    new Date(),
                    qty
            );

            loadData();
            clear();
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;

            controller.update(
                    Integer.parseInt(txtId.getText()),
                    (Product) cbProduct.getSelectedItem(),
                    (Supplier) cbSupplier.getSelectedItem(),
                    new Date(),
                    Integer.parseInt(txtQuantity.getText())
            );
            loadData();
        });

        btnDelete.addActionListener(e -> {

            if (txtId.getText().isEmpty()) return;

            controller.delete(
                    Integer.parseInt(txtId.getText()),
                    (Branch) cbBranch.getSelectedItem() // 👉 truyền branch
            );

            loadData();
            clear();
        });

        btnClear.addActionListener(e -> clear());
    }

    // ===== LOAD COMBO =====
    private void loadCombo() {
        ProductDAOImpl pDao = new ProductDAOImpl();
        SupplierDAOImpl sDao = new SupplierDAOImpl();
        BranchDAOImpl bDao = new BranchDAOImpl();

        cbProduct.setSelectedIndex(-1);
        cbSupplier.setSelectedIndex(-1);
        cbBranch.setSelectedIndex(-1);
        cbProduct.removeAllItems();
        cbSupplier.removeAllItems();
        cbBranch.removeAllItems();

        for (Product p : pDao.findAll()) {
            cbProduct.addItem(p);
        }

        for (Supplier s : sDao.findAll()) {
            cbSupplier.addItem(s);
        }

        for (Branch b : bDao.findAll()) {
            cbBranch.addItem(b);
        }

    }

    // ===== LOAD TABLE =====
    private void loadData() {
        model.setRowCount(0);
        List<Batch> list = controller.getAll();

        for (Batch b : list) {
            model.addRow(new Object[]{
                    b.getBatchID(),
                    b.getProduct().getProductName(),
                    b.getSupplier().getSupplierName(),
                    b.getImportDate(),
                    b.getQuantity()
            });
        }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtId.setText(model.getValueAt(row, 0).toString());
            txtQuantity.setText(model.getValueAt(row, 4).toString());
        }
    }

    private void clear() {
        txtId.setText("");
        txtQuantity.setText("");
        table.clearSelection();
    }
}