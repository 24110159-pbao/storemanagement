package com.example.storemanagement.view;

import com.example.storemanagement.controller.EmployeeController;
import com.example.storemanagement.controller.BranchController;
import com.example.storemanagement.model.entity.Employee;
import com.example.storemanagement.model.entity.Branch;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
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

        txtId = new JTextField();
        txtId.setEnabled(false);

        txtName = new JTextField();
        txtPosition = new JTextField();
        txtSalary = new JTextField();

        cbBranch = new JComboBox<>();
        cbBranch.setSelectedIndex(-1);

        // ===== Lazy load Branch ComboBox khi mở =====
        cbBranch.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                loadBranches();
            }
            @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            @Override public void popupMenuCanceled(PopupMenuEvent e) {}
        });

        form.add(new JLabel("ID:"));
        form.add(txtId);

        form.add(new JLabel("Name:"));
        form.add(txtName);

        form.add(new JLabel("Position:"));
        form.add(txtPosition);

        form.add(new JLabel("Salary:"));
        form.add(txtSalary);

        form.add(new JLabel("Branch:"));
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

        // ===== LOAD TABLE DATA =====
        loadData();

        // ===== EVENTS =====
        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnClear.addActionListener(e -> clear());
    }

    // ===== LOAD BRANCHES =====
    private void loadBranches() {
        Branch selected = (Branch) cbBranch.getSelectedItem(); // giữ lại lựa chọn
        cbBranch.removeAllItems();

        List<Branch> branches = branchController.getAllBranches();
        for (Branch b : branches) {
            cbBranch.addItem(b);
        }

        // restore selection nếu còn hợp lệ
        if (selected != null) {
            for (int i = 0; i < cbBranch.getItemCount(); i++) {
                if (cbBranch.getItemAt(i).getBranchID() == selected.getBranchID()) {
                    cbBranch.setSelectedIndex(i);
                    return;
                }
            }
        }
        cbBranch.setSelectedIndex(-1);
    }

    // ===== LOAD EMPLOYEES =====
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

    // ===== ADD EMPLOYEE =====
    private void addEmployee() {
        try {
            Branch b = (Branch) cbBranch.getSelectedItem();
            if (b == null) {
                JOptionPane.showMessageDialog(this, "Please select a branch!");
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

    // ===== UPDATE EMPLOYEE =====
    private void updateEmployee() {
        if (txtId.getText().isEmpty()) return;

        try {
            Branch b = (Branch) cbBranch.getSelectedItem();
            if (b == null) {
                JOptionPane.showMessageDialog(this, "Please select a branch!");
                return;
            }

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

    // ===== DELETE EMPLOYEE =====
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

            // lazy load nếu chưa load
            if (cbBranch.getItemCount() == 0) {
                loadBranches();
            }

            for (int i = 0; i < cbBranch.getItemCount(); i++) {
                if (cbBranch.getItemAt(i).getBranchName().equals(branchName)) {
                    cbBranch.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    // ===== CLEAR FORM =====
    private void clear() {
        txtId.setText("");
        txtName.setText("");
        txtPosition.setText("");
        txtSalary.setText("");
        cbBranch.setSelectedIndex(-1);
        table.clearSelection();
    }
}