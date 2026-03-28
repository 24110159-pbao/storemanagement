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
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(180, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebar.setBackground(new Color(245, 245, 245));

        // ===== BUTTONS (11 cái) =====
        JButton btnCategory = new JButton("Category");
        JButton btnSupplier = new JButton("Supplier");
        JButton btnCustomer = new JButton("Customer");
        JButton btnEmployee = new JButton("Employee");
        JButton btnProduct = new JButton("Product");
        JButton btnBranch = new JButton("Branch");
        JButton btnBranchAllocation = new JButton("Branch Allocation");
        JButton btnBatch = new JButton("Batch");
        JButton btnCreateInvoice = new JButton("Create Invoice");
        JButton btnStatistics = new JButton("Statistics");
        JButton btnOption = new JButton("Option");

        JButton[] buttons = {
                btnCategory, btnSupplier, btnCustomer, btnEmployee,
                btnProduct, btnBranch, btnBranchAllocation,
                btnBatch, btnCreateInvoice, btnStatistics, btnOption
        };

        Dimension btnSize = new Dimension(150, 40);

        for (JButton btn : buttons) {
            btn.setMaximumSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setBackground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));

            sidebar.add(Box.createVerticalStrut(10));
            sidebar.add(btn);
        }

        add(sidebar, BorderLayout.WEST);

        // ===== CONTENT =====
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // ADD ĐỦ 11 PANEL
        contentPanel.add(new CategoryPanel(), "CATEGORY");
        contentPanel.add(new SupplierPanel(), "SUPPLIER");
        contentPanel.add(new CustomerPanel(), "CUSTOMER");
        contentPanel.add(new EmployeePanel(), "EMPLOYEE");
        contentPanel.add(new ProductPanel(), "PRODUCT");
        contentPanel.add(new BranchPanel(), "BRANCH");
        contentPanel.add(new JLabel("Branch Allocation Panel"), "BRANCH_ALLOCATION");
        contentPanel.add(new BatchPanel(), "BATCH");
        contentPanel.add(new JLabel("Create Invoice Panel"), "CREATE_INVOICE");
        contentPanel.add(new JLabel("Statistics Panel"), "STATISTICS");
        contentPanel.add(new JLabel("Option Panel"), "OPTION");

        add(contentPanel, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnCategory.addActionListener(e -> cardLayout.show(contentPanel, "CATEGORY"));
        btnSupplier.addActionListener(e -> cardLayout.show(contentPanel, "SUPPLIER"));
        btnCustomer.addActionListener(e -> cardLayout.show(contentPanel, "CUSTOMER"));
        btnEmployee.addActionListener(e -> cardLayout.show(contentPanel, "EMPLOYEE"));
        btnProduct.addActionListener(e -> cardLayout.show(contentPanel, "PRODUCT"));
        btnBranch.addActionListener(e -> cardLayout.show(contentPanel, "BRANCH"));
        btnBranchAllocation.addActionListener(e -> cardLayout.show(contentPanel, "BRANCH_ALLOCATION"));
        btnBatch.addActionListener(e -> cardLayout.show(contentPanel, "BATCH"));
        btnCreateInvoice.addActionListener(e -> cardLayout.show(contentPanel, "CREATE_INVOICE"));
        btnStatistics.addActionListener(e -> cardLayout.show(contentPanel, "STATISTICS"));
        btnOption.addActionListener(e -> cardLayout.show(contentPanel, "OPTION"));

    }
}