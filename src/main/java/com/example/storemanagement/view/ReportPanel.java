package com.example.storemanagement.view;

import com.example.storemanagement.controller.ReportController;
import com.example.storemanagement.model.dto.CustomerInvoiceDetailDTO;
import com.example.storemanagement.model.dto.RevenueDTO;
import com.example.storemanagement.model.dto.TopCustomerDTO;
import com.example.storemanagement.model.dto.YearStatisticsDTO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.IntConsumer;

public class ReportPanel extends JPanel {

    private static final Locale DISPLAY_LOCALE = Locale.ENGLISH;
    private static final NumberFormat MONEY = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ReportController controller = new ReportController();
    private final JComboBox<Integer> cboYear = new JComboBox<>();
    private final JLabel lblTotalRevenue = new JLabel();
    private final JLabel lblInvoiceCount = new JLabel();
    private final JLabel lblCustomerCount = new JLabel();
    private final JLabel lblBestMonth = new JLabel();
    private final JLabel lblSelectedMonth = new JLabel();
    private final JLabel lblSelectedRevenue = new JLabel();
    private final JLabel lblSelectedInvoices = new JLabel();
    private final JLabel lblSelectedCustomers = new JLabel();
    private final JButton btnViewCustomers = new ActionButton("View Customers");
    private final DefaultTableModel customerModel = new DefaultTableModel(
            new String[]{"ID", "Customer", "Phone", "Invoices", "Total Spent", "Last Purchase"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable customerTable = new JTable(customerModel);
    private final MonthlyPieChartPanel chartPanel = new MonthlyPieChartPanel();
    private final MonthCustomersDialog customerDialog;
    private final CustomerInvoiceDetailsDialog invoiceDetailsDialog;

    private List<RevenueDTO> monthlyRevenue = new ArrayList<>();
    private List<TopCustomerDTO> currentMonthCustomers = new ArrayList<>();
    private int selectedMonth = LocalDate.now().getMonthValue();

    public ReportPanel() {
        setLayout(new BorderLayout(18, 18));
        setBackground(new Color(9, 20, 46));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCenter(), BorderLayout.CENTER);

        configureTable();
        customerDialog = new MonthCustomersDialog();
        invoiceDetailsDialog = new CustomerInvoiceDetailsDialog();
        chartPanel.setMonthSelectionListener(this::handleMonthSelection);
        cboYear.addActionListener(e -> reloadSelectedYear());
        btnViewCustomers.addActionListener(e -> showCustomerDialog());
        loadYears();
    }

    private JComponent buildHeader() {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(16, 16), 28, new Color(18, 36, 82));
        panel.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Annual Statistics Dashboard");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        JLabel subtitle = new JLabel("View yearly statistics with a 12-month pie chart and customers for the selected month.");
        subtitle.setForeground(new Color(194, 208, 255));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        text.add(title);
        text.add(Box.createVerticalStrut(6));
        text.add(subtitle);

        RoundedPanel filter = new RoundedPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0), 22, new Color(24, 46, 101));
        filter.setBorder(new EmptyBorder(14, 16, 14, 16));
        JLabel lbYear = new JLabel("Year");
        lbYear.setForeground(new Color(220, 229, 255));
        lbYear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cboYear.setPreferredSize(new Dimension(110, 34));
        JButton btnReload = new ActionButton("Reload");
        btnReload.addActionListener(e -> reloadSelectedYear());
        filter.add(lbYear);
        filter.add(cboYear);
        filter.add(btnReload);

        panel.add(text, BorderLayout.CENTER);
        panel.add(filter, BorderLayout.EAST);
        return panel;
    }

    private JComponent buildCenter() {
        JPanel center = new JPanel(new BorderLayout(18, 18));
        center.setOpaque(false);

        JPanel metrics = new JPanel(new GridLayout(1, 4, 16, 0));
        metrics.setOpaque(false);
        metrics.add(createMetricCard("Annual Revenue", lblTotalRevenue));
        metrics.add(createMetricCard("Invoices", lblInvoiceCount));
        metrics.add(createMetricCard("Active Customers", lblCustomerCount));
        metrics.add(createMetricCard("Top Month", lblBestMonth));

        JPanel top = new JPanel(new GridLayout(1, 2, 18, 0));
        top.setOpaque(false);
        top.add(buildChartCard());
        top.add(buildMonthCard());
        top.setPreferredSize(new Dimension(0, 340));

        center.add(metrics, BorderLayout.NORTH);
        JPanel lower = new JPanel(new BorderLayout(0, 18));
        lower.setOpaque(false);
        lower.add(top, BorderLayout.NORTH);

        center.add(lower, BorderLayout.CENTER);
        return center;
    }

    private RoundedPanel createMetricCard(String titleText, JLabel valueLabel) {
        RoundedPanel card = new RoundedPanel(new BorderLayout(0, 10), 24, new Color(255, 255, 255, 235));
        card.setBorder(new EmptyBorder(16, 18, 16, 18));
        JLabel title = new JLabel(titleText);
        title.setForeground(new Color(68, 82, 122));
        title.setFont(new Font("Segoe UI", Font.BOLD, 13));
        valueLabel.setForeground(new Color(15, 45, 118));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        card.add(title, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JComponent buildChartCard() {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(0, 16), 28, new Color(245, 248, 255));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("12-Month Pie Chart");
        title.setForeground(new Color(17, 37, 83));
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel hint = new JLabel("Click a slice to select a month.");
        hint.setForeground(new Color(95, 109, 148));
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(hint);
        panel.add(header, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(0, 370));
        return panel;
    }

    private JComponent buildMonthCard() {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(0, 18), 28, new Color(16, 33, 74));
        panel.setBorder(new EmptyBorder(22, 22, 22, 22));

        lblSelectedMonth.setForeground(Color.WHITE);
        lblSelectedMonth.setFont(new Font("Segoe UI", Font.BOLD, 24));
        JLabel subtitle = new JLabel("Details for the month selected from the chart");
        subtitle.setForeground(new Color(186, 198, 236));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(lblSelectedMonth);
        header.add(Box.createVerticalStrut(6));
        header.add(subtitle);

        JPanel stats = new JPanel(new GridLayout(3, 1, 0, 12));
        stats.setOpaque(false);
        stats.add(createMonthRow("Revenue", lblSelectedRevenue));
        stats.add(createMonthRow("Invoices", lblSelectedInvoices));
        stats.add(createMonthRow("Customers", lblSelectedCustomers));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        actions.setOpaque(false);
        actions.add(btnViewCustomers);

        panel.add(header, BorderLayout.NORTH);
        panel.add(stats, BorderLayout.CENTER);
        panel.add(actions, BorderLayout.SOUTH);
        return panel;
    }

    private JComponent createMonthRow(String text, JLabel value) {
        RoundedPanel row = new RoundedPanel(new BorderLayout(), 18, new Color(26, 50, 109));
        row.setBorder(new EmptyBorder(14, 16, 14, 16));
        JLabel label = new JLabel(text);
        label.setForeground(new Color(188, 201, 240));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        value.setForeground(Color.WHITE);
        value.setFont(new Font("Segoe UI", Font.BOLD, 16));
        row.add(label, BorderLayout.WEST);
        row.add(value, BorderLayout.EAST);
        return row;
    }

    private void configureTable() {
        customerTable.setRowHeight(32);
        customerTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        customerTable.setBackground(Color.WHITE);
        customerTable.setForeground(new Color(35, 49, 86));
        customerTable.setSelectionBackground(new Color(220, 232, 255));
        customerTable.setSelectionForeground(new Color(20, 36, 72));
        customerTable.setGridColor(new Color(231, 236, 246));
        customerTable.setShowVerticalLines(false);
        customerTable.setIntercellSpacing(new Dimension(0, 1));
        JTableHeader header = customerTable.getTableHeader();
        header.setBackground(new Color(230, 238, 255));
        header.setForeground(new Color(23, 44, 94));
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setReorderingAllowed(false);
        customerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int viewRow = customerTable.rowAtPoint(e.getPoint());
                if (viewRow >= 0) {
                    customerTable.setRowSelectionInterval(viewRow, viewRow);
                    openSelectedCustomerInvoices(viewRow);
                }
            }
        });
    }

    private void loadYears() {
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        List<Integer> years = controller.getAvailableYears();
        for (Integer year : years) {
            model.addElement(year);
        }
        cboYear.setModel(model);
        if (!years.isEmpty()) {
            cboYear.setSelectedItem(years.get(0));
            loadReport(years.get(0));
        }
    }

    private void reloadSelectedYear() {
        Integer year = (Integer) cboYear.getSelectedItem();
        if (year != null) {
            loadReport(year);
        }
    }

    private void loadReport(int year) {
        YearStatisticsDTO statistics = controller.getYearStatistics(year);
        monthlyRevenue = statistics.getMonthlyRevenues();
        selectedMonth = statistics.getBestMonth() >= 1 ? statistics.getBestMonth() : LocalDate.now().getMonthValue();
        chartPanel.setData(monthlyRevenue, selectedMonth);

        lblTotalRevenue.setText(formatMoney(statistics.getTotalRevenue()));
        lblInvoiceCount.setText(String.valueOf(statistics.getTotalInvoices()));
        lblCustomerCount.setText(String.valueOf(statistics.getTotalCustomers()));
        lblBestMonth.setText(monthLabel(statistics.getBestMonth()));
        updateSelectedMonthView();
    }

    private void handleMonthSelection(int month) {
        selectedMonth = month;
        chartPanel.setSelectedMonth(month);
        updateSelectedMonthView();
    }

    private void updateSelectedMonthView() {
        RevenueDTO monthData = monthlyRevenue.stream()
                .filter(item -> item.getMonth() == selectedMonth)
                .findFirst()
                .orElse(new RevenueDTO(selectedMonth, BigDecimal.ZERO, 0));
        Integer year = (Integer) cboYear.getSelectedItem();
        List<TopCustomerDTO> customers = year == null ? List.of() : controller.getTopCustomersByMonth(year, selectedMonth);
        currentMonthCustomers = customers;

        lblSelectedMonth.setText("Month " + selectedMonth + " - " + monthLabel(selectedMonth));
        lblSelectedRevenue.setText(formatMoney(monthData.getRevenue()));
        lblSelectedInvoices.setText(String.valueOf(monthData.getInvoiceCount()));
        lblSelectedCustomers.setText(String.valueOf(customers.size()));

        customerModel.setRowCount(0);
        for (TopCustomerDTO customer : customers) {
            customerModel.addRow(new Object[]{
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getPhone(),
                    customer.getInvoiceCount(),
                    formatMoney(customer.getTotalSpent()),
                    customer.getLastPurchaseDate() != null ? DATE.format(customer.getLastPurchaseDate()) : "--"
            });
        }

        if (customerDialog.isVisible()) {
            customerDialog.refresh();
        }
        if (invoiceDetailsDialog.isVisible()) {
            invoiceDetailsDialog.reload();
        }
    }

    private String monthLabel(int month) {
        if (month < 1 || month > 12) {
            return "No data";
        }
        return Month.of(month).getDisplayName(TextStyle.FULL, DISPLAY_LOCALE);
    }

    private String formatMoney(BigDecimal value) {
        return MONEY.format(value != null ? value : BigDecimal.ZERO);
    }

    private void showCustomerDialog() {
        customerDialog.refresh();
        customerDialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(this));
        customerDialog.setVisible(true);
    }

    private void openSelectedCustomerInvoices(int viewRow) {
        int modelRow = customerTable.convertRowIndexToModel(viewRow);
        if (modelRow < 0 || modelRow >= currentMonthCustomers.size()) {
            return;
        }
        TopCustomerDTO customer = currentMonthCustomers.get(modelRow);
        invoiceDetailsDialog.showForCustomer(customer);
    }

    private static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bg;

        private RoundedPanel(LayoutManager layout, int radius, Color bg) {
            super(layout);
            this.radius = radius;
            this.bg = bg;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(6, 16, 40, 32));
            g2.fillRoundRect(3, 6, getWidth() - 6, getHeight() - 6, radius, radius);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight() - 3, radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class ActionButton extends JButton {
        private ActionButton(String text) {
            super(text);
            setForeground(Color.WHITE);
            setBackground(new Color(79, 127, 255));
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorder(new EmptyBorder(9, 16, 9, 16));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private class MonthCustomersDialog extends JDialog {
        private final JLabel lblDialogTitle = new JLabel();
        private final JLabel lblDialogHint = new JLabel();

        private MonthCustomersDialog() {
            super(SwingUtilities.getWindowAncestor(ReportPanel.this), "Monthly Customers", Dialog.ModalityType.MODELESS);
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            setSize(760, 420);
            setMinimumSize(new Dimension(680, 360));
            setLayout(new BorderLayout(0, 14));
            getContentPane().setBackground(new Color(245, 248, 255));

            JPanel header = new JPanel();
            header.setOpaque(false);
            header.setBorder(new EmptyBorder(16, 18, 0, 18));
            header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
            lblDialogTitle.setForeground(new Color(17, 37, 83));
            lblDialogTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblDialogHint.setForeground(new Color(95, 109, 148));
            lblDialogHint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            header.add(lblDialogTitle);
            header.add(Box.createVerticalStrut(4));
            header.add(lblDialogHint);

            JScrollPane scrollPane = new JScrollPane(customerTable);
            scrollPane.setBorder(new EmptyBorder(0, 18, 18, 18));
            scrollPane.getViewport().setBackground(Color.WHITE);

            add(header, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
        }

        private void refresh() {
            Integer year = (Integer) cboYear.getSelectedItem();
            String yearText = year != null ? String.valueOf(year) : "--";
            lblDialogTitle.setText("Customers in month " + selectedMonth);
            lblDialogHint.setText(monthLabel(selectedMonth) + " / " + yearText + " - " + customerModel.getRowCount() + " customers");
        }
    }

    private class CustomerInvoiceDetailsDialog extends JDialog {
        private final JLabel lblTitle = new JLabel();
        private final JLabel lblHint = new JLabel();
        private final DefaultTableModel invoiceDetailsModel = new DefaultTableModel(
                new String[]{"Invoice ID", "Date", "Product", "Qty", "Unit Price", "Line Total", "Invoice Total"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        private final JTable invoiceDetailsTable = new JTable(invoiceDetailsModel);
        private TopCustomerDTO selectedCustomer;

        private CustomerInvoiceDetailsDialog() {
            super(SwingUtilities.getWindowAncestor(ReportPanel.this), "Customer Invoice Details", Dialog.ModalityType.MODELESS);
            setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
            setSize(920, 460);
            setMinimumSize(new Dimension(780, 360));
            setLayout(new BorderLayout(0, 14));
            getContentPane().setBackground(new Color(245, 248, 255));

            JPanel header = new JPanel();
            header.setOpaque(false);
            header.setBorder(new EmptyBorder(16, 18, 0, 18));
            header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
            lblTitle.setForeground(new Color(17, 37, 83));
            lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
            lblHint.setForeground(new Color(95, 109, 148));
            lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            header.add(lblTitle);
            header.add(Box.createVerticalStrut(4));
            header.add(lblHint);

            invoiceDetailsTable.setRowHeight(30);
            invoiceDetailsTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            invoiceDetailsTable.setBackground(Color.WHITE);
            invoiceDetailsTable.setForeground(new Color(35, 49, 86));
            invoiceDetailsTable.setSelectionBackground(new Color(220, 232, 255));
            invoiceDetailsTable.setSelectionForeground(new Color(20, 36, 72));
            invoiceDetailsTable.setGridColor(new Color(231, 236, 246));
            invoiceDetailsTable.setShowVerticalLines(false);
            invoiceDetailsTable.setIntercellSpacing(new Dimension(0, 1));
            JTableHeader headerTable = invoiceDetailsTable.getTableHeader();
            headerTable.setBackground(new Color(230, 238, 255));
            headerTable.setForeground(new Color(23, 44, 94));
            headerTable.setFont(new Font("Segoe UI", Font.BOLD, 13));
            headerTable.setReorderingAllowed(false);

            JScrollPane scrollPane = new JScrollPane(invoiceDetailsTable);
            scrollPane.setBorder(new EmptyBorder(0, 18, 18, 18));
            scrollPane.getViewport().setBackground(Color.WHITE);

            add(header, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
        }

        private void showForCustomer(TopCustomerDTO customer) {
            this.selectedCustomer = customer;
            reload();
            setLocationRelativeTo(SwingUtilities.getWindowAncestor(ReportPanel.this));
            setVisible(true);
        }

        private void reload() {
            invoiceDetailsModel.setRowCount(0);
            Integer year = (Integer) cboYear.getSelectedItem();

            if (selectedCustomer == null || year == null) {
                lblTitle.setText("Customer invoice details");
                lblHint.setText("No customer selected");
                return;
            }

            List<CustomerInvoiceDetailDTO> details = controller.getCustomerInvoiceDetailsByMonth(
                    year, selectedMonth, selectedCustomer.getCustomerId()
            );

            for (CustomerInvoiceDetailDTO detail : details) {
                invoiceDetailsModel.addRow(new Object[]{
                        detail.getInvoiceId(),
                        detail.getInvoiceDate() != null ? DATE.format(detail.getInvoiceDate()) : "--",
                        detail.getProductName(),
                        detail.getQuantity(),
                        formatMoney(detail.getUnitPrice()),
                        formatMoney(detail.getLineTotal()),
                        formatMoney(detail.getInvoiceTotal())
                });
            }

            lblTitle.setText("Invoices of " + selectedCustomer.getCustomerName());
            lblHint.setText(monthLabel(selectedMonth) + " / " + year + " - " + details.size() + " item rows");
        }
    }

    private static class MonthlyPieChartPanel extends JPanel {
        private static final Color[] COLORS = {
                new Color(72, 119, 255), new Color(80, 181, 255), new Color(74, 214, 201),
                new Color(112, 223, 141), new Color(245, 194, 82), new Color(255, 160, 94),
                new Color(255, 119, 124), new Color(226, 116, 255), new Color(155, 132, 255),
                new Color(123, 156, 255), new Color(70, 207, 239), new Color(96, 211, 168)
        };
        private List<RevenueDTO> data = new ArrayList<>();
        private int selectedMonth = 1;
        private IntConsumer listener;

        private MonthlyPieChartPanel() {
            setOpaque(false);
            setPreferredSize(new Dimension(420, 290));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int month = resolveMonthAt(e.getX(), e.getY());
                    if (month > 0 && listener != null) {
                        listener.accept(month);
                    }
                }
            });
        }

        private void setData(List<RevenueDTO> data, int selectedMonth) {
            this.data = data != null ? data : new ArrayList<>();
            this.selectedMonth = selectedMonth;
            repaint();
        }

        private void setSelectedMonth(int selectedMonth) {
            this.selectedMonth = selectedMonth;
            repaint();
        }

        private void setMonthSelectionListener(IntConsumer listener) {
            this.listener = listener;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            int size = Math.min(getWidth(), getHeight()) - 70;
            int outer = size;
            int inner = (int) (size * 0.53);
            int cx = getWidth() / 2;
            int cy = getHeight() / 2 + 6;
            int x = cx - outer / 2;
            int y = cy - outer / 2;
            BigDecimal maxRevenue = data.stream().map(RevenueDTO::getRevenue).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
            double part = 360.0 / 12.0;

            g2.setColor(new Color(235, 240, 252));
            g2.fillOval(x - 10, y - 10, outer + 20, outer + 20);

            for (int month = 1; month <= 12; month++) {
                Color fill = month == selectedMonth
                        ? COLORS[month - 1]
                        : new Color(220, 227, 241);
                Shape ring = ring(x, y, outer, inner, 90 - ((month - 1) * part), -part);
                g2.setColor(fill);
                g2.fill(ring);
                g2.setColor(month == selectedMonth ? new Color(255, 255, 255, 200) : new Color(255, 255, 255, 150));
                g2.setStroke(new BasicStroke(month == selectedMonth ? 4f : 2f));
                g2.draw(ring);
            }

            g2.setColor(new Color(17, 37, 83));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
            drawCentered(g2, "T" + selectedMonth, new Rectangle(cx - 60, cy - 28, 120, 28));
            g2.setColor(new Color(95, 109, 148));
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            drawCentered(g2, "Click a month", new Rectangle(cx - 70, cy + 6, 140, 18));

            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            for (int month = 1; month <= 12; month++) {
                double angle = Math.toRadians(90 - ((month - 0.5) * part));
                int r = outer / 2 + 26;
                int tx = cx + (int) (Math.cos(angle) * r);
                int ty = cy - (int) (Math.sin(angle) * r);
                String label = "T" + month;
                FontMetrics m = g2.getFontMetrics();
                g2.setColor(month == selectedMonth ? new Color(17, 37, 83) : new Color(108, 122, 161));
                g2.drawString(label, tx - m.stringWidth(label) / 2, ty);
            }
            g2.dispose();
        }

        private int resolveMonthAt(int x, int y) {
            int size = Math.min(getWidth(), getHeight()) - 70;
            int outer = size;
            int inner = (int) (size * 0.53);
            int cx = getWidth() / 2;
            int cy = getHeight() / 2 + 6;
            double dx = x - cx;
            double dy = cy - y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < inner / 2.0 || distance > outer / 2.0) {
                return -1;
            }
            double angle = Math.toDegrees(Math.atan2(dy, dx));
            double normalized = (90 - angle + 360) % 360;
            return (int) (normalized / 30.0) + 1;
        }

        private RevenueDTO getMonthData(int month) {
            for (RevenueDTO item : data) {
                if (item.getMonth() == month) {
                    return item;
                }
            }
            return new RevenueDTO(month, BigDecimal.ZERO, 0);
        }

        private Shape ring(int x, int y, int outer, int inner, double start, double extent) {
            Arc2D arc = new Arc2D.Double(x, y, outer, outer, start, extent, Arc2D.PIE);
            int offset = (outer - inner) / 2;
            Ellipse2D hole = new Ellipse2D.Double(x + offset, y + offset, inner, inner);
            Area area = new Area(arc);
            area.subtract(new Area(hole));
            return area;
        }

        private void drawCentered(Graphics2D g2, String text, Rectangle rect) {
            FontMetrics m = g2.getFontMetrics();
            int tx = rect.x + (rect.width - m.stringWidth(text)) / 2;
            int ty = rect.y + ((rect.height - m.getHeight()) / 2) + m.getAscent();
            g2.drawString(text, tx, ty);
        }
    }
    public void refreshData() {
        loadYears();
        reloadSelectedYear();
    }
}
