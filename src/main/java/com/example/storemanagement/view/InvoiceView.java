package com.example.storemanagement.view;

import com.example.storemanagement.controller.InvoiceController;
import com.example.storemanagement.model.entity.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InvoiceView extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtProduct;
    private JTextField txtQuantity;
    private JLabel lblTotal;

    private InvoiceController controller = new InvoiceController();

    private List<InvoiceDetail> details = new ArrayList<>();

    public InvoiceView() {
        setTitle("Quản lý hóa đơn");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());

        // TOP INPUT
        JPanel top = new JPanel();
        txtProduct = new JTextField(10);
        txtQuantity = new JTextField(5);

        JButton btnAdd = new JButton("Thêm");

        top.add(new JLabel("ProductID"));
        top.add(txtProduct);
        top.add(new JLabel("Quantity"));
        top.add(txtQuantity);
        top.add(btnAdd);

        panel.add(top, BorderLayout.NORTH);

        // TABLE
        model = new DefaultTableModel(new String[]{
                "ProductID", "Quantity", "UnitPrice", "Total"
        }, 0);

        table = new JTable(model);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // BOTTOM
        JPanel bottom = new JPanel();

        lblTotal = new JLabel("Total: 0");
        JButton btnSave = new JButton("Lưu hóa đơn");

        bottom.add(lblTotal);
        bottom.add(btnSave);

        panel.add(bottom, BorderLayout.SOUTH);

        add(panel);

        // EVENT
        btnAdd.addActionListener(e -> addProduct());
        btnSave.addActionListener(e -> saveInvoice());
    }
    private void addProduct() {
        try {
            int productId = Integer.parseInt(txtProduct.getText());
            int quantity = Integer.parseInt(txtQuantity.getText());

            // giả lập giá
            BigDecimal price = new BigDecimal("100");

            InvoiceDetail detail = new InvoiceDetail();
            detail.setQuantity(quantity);
            detail.setUnitPrice(price);

            details.add(detail);

            BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));

            model.addRow(new Object[]{
                    productId, quantity, price, total
            });

            updateTotal();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập dữ liệu");
        }
    }
    private void updateTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (InvoiceDetail d : details) {
            total = total.add(
                    d.getUnitPrice()
                            .multiply(BigDecimal.valueOf(d.getQuantity()))
            );
        }

        lblTotal.setText("Total: " + total);
    }
    private void saveInvoice() {

        Invoice invoice = new Invoice();
        invoice.setDetails(details);

        controller.createInvoice(invoice);

        JOptionPane.showMessageDialog(this, "Lưu thành công!");

        // reset
        model.setRowCount(0);
        details.clear();
        lblTotal.setText("Total: 0");
    }
}