package com.example.storemanagement.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainFrame() {
        setTitle("Sales Management System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(10, 1));
        sidebar.setPreferredSize(new Dimension(150, 0));

        JButton btnProduct = new JButton("Product");
        JButton btnCategory = new JButton("Category");
        JButton btnSupplier = new JButton("Supplier");
        JButton btnCustomer = new JButton("Customer");
        JButton btnInvoice = new JButton("Invoice");
        JButton btnBranch = new JButton("Branch");
        JButton btnEmployee = new JButton("Employee");
        JButton btnStatistics = new JButton("Statistics");

        sidebar.add(btnProduct);
        sidebar.add(btnCategory);
        sidebar.add(btnSupplier);
        sidebar.add(btnCustomer);
        sidebar.add(btnInvoice);
        sidebar.add(btnBranch);
        sidebar.add(btnEmployee);
        sidebar.add(btnStatistics);

        add(sidebar, BorderLayout.WEST);

        // ===== CONTENT =====
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);


        contentPanel.add(new CategoryPanel(), "CATEGORY");


        add(contentPanel, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnProduct.addActionListener(e -> cardLayout.show(contentPanel, "PRODUCT"));
        btnCategory.addActionListener(e -> cardLayout.show(contentPanel, "CATEGORY"));
        btnSupplier.addActionListener(e -> cardLayout.show(contentPanel, "SUPPLIER"));
        btnCustomer.addActionListener(e -> cardLayout.show(contentPanel, "CUSTOMER"));
        btnInvoice.addActionListener(e -> cardLayout.show(contentPanel, "INVOICE"));
        btnBranch.addActionListener(e -> cardLayout.show(contentPanel, "BRANCH"));
        btnEmployee.addActionListener(e -> cardLayout.show(contentPanel, "EMPLOYEE"));
        btnStatistics.addActionListener(e -> cardLayout.show(contentPanel, "STATISTICS"));
    }
}
