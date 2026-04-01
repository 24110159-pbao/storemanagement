package com.example.storemanagement.view;

import com.example.storemanagement.controller.InvoiceController;
import com.example.storemanagement.model.dto.InvoiceInputDTO;
import com.example.storemanagement.model.dto.InvoiceItemDTO;
import com.example.storemanagement.model.entity.*;
import com.example.storemanagement.config.JpaUtil;
import com.example.storemanagement.util.PdfGenerator;

import jakarta.persistence.EntityManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 * JPanel tạo hóa đơn mới.
 * Cho phép chọn Customer, Employee, Branch, thêm Products, tính tổng, và xuất PDF.
 */
public class CreateInvoiecPanel extends JPanel {

    // ===== COLORS =====
    private final Color PRIMARY = new Color(33, 102, 140);
    private final Color LIGHT_BG = new Color(240, 240, 240);
    private final Color TABLE_HEADER = new Color(90, 90, 90);
    private final Color BTN_ADD = new Color(40, 167, 69);
    private final Color BTN_DELETE = new Color(220, 53, 69);
    private final Color BTN_CREATE = new Color(255, 133, 27);

    // ===== UI COMPONENTS =====
    private JComboBox<Customer> cbCustomer;
    private JComboBox<Employee> cbEmployee;
    private JComboBox<Branch> cbBranch;
    private JComboBox<Product> cbProduct;

    private JTextField txtQuantity;
    private JLabel lblTotal;
    private JTable table;
    private DefaultTableModel model;

    // ===== CONTROLLER =====
    private InvoiceController controller = new InvoiceController();

    public CreateInvoiecPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(LIGHT_BG);

        initTopPanel();
        initTable();
        initBottomPanel();
    }

    // ===== INIT TOP PANEL (ComboBox + Labels) =====
    private void initTopPanel() {
        JPanel top = new JPanel(new GridLayout(2, 4, 10, 10));
        top.setBackground(PRIMARY);
        top.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Invoice Info",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                Color.WHITE
        ));

        cbCustomer = new JComboBox<>(loadCustomers());
        cbEmployee = new JComboBox<>(loadEmployees());
        cbBranch = new JComboBox<>(loadBranches());
        cbProduct = new JComboBox<>(loadProducts());

        cbCustomer.setSelectedIndex(-1);
        cbEmployee.setSelectedIndex(-1);
        cbBranch.setSelectedIndex(-1);
        cbProduct.setSelectedIndex(-1);

        // Bật autocomplete
        AutoCompleteDecorator.decorate(cbCustomer);
        AutoCompleteDecorator.decorate(cbEmployee);
        AutoCompleteDecorator.decorate(cbBranch);
        AutoCompleteDecorator.decorate(cbProduct);

        JLabel[] labels = {
                new JLabel("Customer"),
                new JLabel("Employee"),
                new JLabel("Branch"),
                new JLabel("Product")
        };

        for (JLabel lb : labels) lb.setForeground(Color.WHITE);

        top.add(labels[0]); top.add(labels[1]);
        top.add(labels[2]); top.add(labels[3]);

        top.add(cbCustomer); top.add(cbEmployee);
        top.add(cbBranch); top.add(cbProduct);

        add(top, BorderLayout.NORTH);
    }

    // ===== INIT TABLE =====
    private void initTable() {
        model = new DefaultTableModel(new String[]{"Product", "Qty", "Price", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(184, 207, 229));

        table.getTableHeader().setBackground(TABLE_HEADER);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // ===== INIT BOTTOM PANEL (Buttons + Quantity + Total) =====
    private void initBottomPanel() {
        JPanel bottom = new JPanel();
        bottom.setBackground(LIGHT_BG);

        txtQuantity = new JTextField(5);

        JButton btnAdd = createButton("Add Item", BTN_ADD);
        JButton btnRemove = createButton("Remove Item", BTN_DELETE);
        JButton btnCreate = createButton("Create Invoice", BTN_CREATE);

        lblTotal = new JLabel("Total: 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));

        bottom.add(new JLabel("Quantity:"));
        bottom.add(txtQuantity);
        bottom.add(btnAdd);
        bottom.add(btnRemove);
        bottom.add(btnCreate);
        bottom.add(lblTotal);

        add(bottom, BorderLayout.SOUTH);

        // ===== EVENTS =====
        btnAdd.addActionListener(e -> addItem());
        btnRemove.addActionListener(e -> removeItem());
        btnCreate.addActionListener(e -> createInvoice());
    }

    // ===== CREATE BUTTON UTILITY =====
    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }

    // ===== LOAD DATA FROM DB =====
    private Customer[] loadCustomers() {
        EntityManager em = JpaUtil.getEntityManager();
        List<Customer> list = em.createQuery("FROM Customer", Customer.class).getResultList();
        em.close();
        return list.toArray(new Customer[0]);
    }

    private Employee[] loadEmployees() {
        EntityManager em = JpaUtil.getEntityManager();
        List<Employee> list = em.createQuery("FROM Employee", Employee.class).getResultList();
        em.close();
        return list.toArray(new Employee[0]);
    }

    private Branch[] loadBranches() {
        EntityManager em = JpaUtil.getEntityManager();
        List<Branch> list = em.createQuery("FROM Branch", Branch.class).getResultList();
        em.close();
        return list.toArray(new Branch[0]);
    }

    private Product[] loadProducts() {
        EntityManager em = JpaUtil.getEntityManager();
        List<Product> list = em.createQuery("FROM Product", Product.class).getResultList();
        em.close();
        return list.toArray(new Product[0]);
    }

    // ===== ADD ITEM TO TABLE =====
    private void addItem() {
        try {
            Product p = (Product) cbProduct.getSelectedItem();
            Branch b = (Branch) cbBranch.getSelectedItem();
            if (p == null || b == null) { JOptionPane.showMessageDialog(this, "Chọn Product và Branch!"); return; }
            int qty = Integer.parseInt(txtQuantity.getText());
            if (qty <= 0) { JOptionPane.showMessageDialog(this, "Quantity phải > 0"); return; }

            boolean ok = controller.checkStock(b.getBranchID().longValue(),
                    Long.valueOf(p.getProductID()), qty);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Không đủ hàng tại chi nhánh!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BigDecimal price = p.getUnitPrice();
            BigDecimal total = price.multiply(BigDecimal.valueOf(qty));

            model.addRow(new Object[]{p, qty, price, total});
            updateTotal();
            txtQuantity.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity không hợp lệ!");
        }
    }

    // ===== REMOVE ITEM =====
    private void removeItem() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Chọn dòng để xóa!"); return; }
        model.removeRow(row);
        updateTotal();
    }

    // ===== UPDATE TOTAL =====
    private void updateTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < model.getRowCount(); i++) {
            sum = sum.add((BigDecimal) model.getValueAt(i, 3));
        }
        lblTotal.setText("Total: " + sum);
    }

    // ===== CREATE INVOICE =====
    private void createInvoice() {
        try {
            if (model.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Chưa có sản phẩm!"); return; }

            Customer c = (Customer) cbCustomer.getSelectedItem();
            Employee e = (Employee) cbEmployee.getSelectedItem();
            Branch b = (Branch) cbBranch.getSelectedItem();

            InvoiceInputDTO dto = new InvoiceInputDTO();
            dto.setCustomerId(c.getCustomerID());
            dto.setEmployeeId(e.getEmployeeID());
            dto.setBranchId(b.getBranchID());
            dto.setInvoiceDate(LocalDate.now());

            List<InvoiceItemDTO> items = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                Product p = (Product) model.getValueAt(i, 0);
                int qty = (int) model.getValueAt(i, 1);
                BigDecimal price = (BigDecimal) model.getValueAt(i, 2);
                items.add(new InvoiceItemDTO(p.getProductID(), qty, price.doubleValue()));
            }
            dto.setItems(items);

            controller.createInvoice(dto);
            String filePath = "invoice_" + System.currentTimeMillis() + ".pdf";
            PdfGenerator.exportInvoice(dto, filePath);

            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!\nPDF: " + filePath);
            model.setRowCount(0);
            updateTotal();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== REFRESH DATA =====
    public void refreshData() {
        cbCustomer.setModel(new DefaultComboBoxModel<>(loadCustomers()));
        cbEmployee.setModel(new DefaultComboBoxModel<>(loadEmployees()));
        cbBranch.setModel(new DefaultComboBoxModel<>(loadBranches()));
        cbProduct.setModel(new DefaultComboBoxModel<>(loadProducts()));

        cbCustomer.setSelectedIndex(-1);
        cbEmployee.setSelectedIndex(-1);
        cbBranch.setSelectedIndex(-1);
        cbProduct.setSelectedIndex(-1);
    }
}