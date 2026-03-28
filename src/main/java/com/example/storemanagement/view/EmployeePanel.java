package com.example.storemanagement.view;

import com.example.storemanagement.controller.EmployeeController;
import com.example.storemanagement.controller.BranchController;
import com.example.storemanagement.model.entity.Employee;
import com.example.storemanagement.model.entity.Branch;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtName, txtPosition, txtSalary;
    private JComboBox<Branch> cbBranch;

    private EmployeeController controller = new EmployeeController();
    private BranchController branchController = new BranchController();

    public EmployeePanel() {
        setLayout(new BorderLayout());

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        form.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEnabled(false);
        form.add(txtId);

        form.add(new JLabel("Name:"));
        txtName = new JTextField();
        form.add(txtName);

        form.add(new JLabel("Position:"));
        txtPosition = new JTextField();
        form.add(txtPosition);

        form.add(new JLabel("Salary:"));
        txtSalary = new JTextField();
        form.add(txtSalary);

        form.add(new JLabel("Branch:"));
        cbBranch = new JComboBox<>();
        form.add(cbBranch);

        add(form, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Position", "Salary", "Branch"}, 0
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
        loadBranches();
        loadData();

        // ===== EVENTS =====
        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnClear.addActionListener(e -> clear());
    }

    // ===== LOAD BRANCH =====
    private void loadBranches() {
        cbBranch.removeAllItems();
        List<Branch> branches = branchController.getAllBranches();

        for (Branch b : branches) {
            cbBranch.addItem(b);
        }

        cbBranch.setSelectedIndex(-1);
    }

    // ===== LOAD EMPLOYEE =====
    private void loadData() {
        model.setRowCount(0);
        List<Employee> list = controller.getAllEmployees();

        for (Employee e : list) {
            model.addRow(new Object[]{
                    e.getEmployeeID(),
                    e.getEmployeeName(),
                    e.getPosition(),
                    e.getSalary(),
                    e.getBranch() != null ? e.getBranch().getBranchName() : ""
            });
        }
    }

    // ===== ADD =====
    private void addEmployee() {
        try {
            Branch b = (Branch) cbBranch.getSelectedItem();

            if (b == null) {
                JOptionPane.showMessageDialog(this, "Please select branch!");
                return;
            }

            controller.addEmployee(
                    txtName.getText(),
                    txtPosition.getText(),
                    Double.parseDouble(txtSalary.getText()),
                    b.getBranchID()
            );

            loadData();
            clear();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    // ===== UPDATE =====
    private void updateEmployee() {
        if (txtId.getText().isEmpty()) return;

        try {
            Branch b = (Branch) cbBranch.getSelectedItem();

            controller.updateEmployee(
                    Integer.parseInt(txtId.getText()),
                    txtName.getText(),
                    txtPosition.getText(),
                    Double.parseDouble(txtSalary.getText()),
                    b.getBranchID()
            );

            loadData();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    // ===== DELETE =====
    private void deleteEmployee() {
        if (txtId.getText().isEmpty()) return;

        controller.deleteEmployee(Integer.parseInt(txtId.getText()));
        loadData();
        clear();
    }

    // ===== FILL FORM =====
    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtId.setText(model.getValueAt(row, 0).toString());
            txtName.setText(model.getValueAt(row, 1).toString());
            txtPosition.setText(model.getValueAt(row, 2).toString());
            txtSalary.setText(model.getValueAt(row, 3).toString());

            String branchName = model.getValueAt(row, 4).toString();

            for (int i = 0; i < cbBranch.getItemCount(); i++) {
                if (cbBranch.getItemAt(i).getBranchName().equals(branchName)) {
                    cbBranch.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    // ===== CLEAR =====
    private void clear() {
        txtId.setText("");
        txtName.setText("");
        txtPosition.setText("");
        txtSalary.setText("");
        cbBranch.setSelectedIndex(-1);
        table.clearSelection();
    }
}