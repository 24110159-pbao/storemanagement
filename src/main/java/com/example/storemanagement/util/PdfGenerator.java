package com.example.storemanagement.util;

import com.example.storemanagement.model.dto.InvoiceInputDTO;
import com.example.storemanagement.model.dto.InvoiceItemDTO;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PdfGenerator {

    public static void exportInvoice(InvoiceInputDTO dto, String path) {

        try {
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // ===== FORMAT TIỀN =====
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            // ===== TITLE =====
            Paragraph title = new Paragraph("STORE INVOICE")
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(title);
            document.add(new Paragraph(" "));

            // ===== INFO =====
            document.add(new Paragraph("Customer ID: " + dto.getCustomerId()));
            document.add(new Paragraph("Employee ID: " + dto.getEmployeeId()));
            document.add(new Paragraph("Branch ID: " + dto.getBranchId()));
            document.add(new Paragraph("Date: " +
                    dto.getInvoiceDate().format(DateTimeFormatter.ISO_DATE)));

            document.add(new Paragraph(" "));

            // ===== TABLE =====
            Table table = new Table(4);
            table.setWidth(com.itextpdf.layout.properties.UnitValue.createPercentValue(100));

            table.addHeaderCell("Product");
            table.addHeaderCell("Qty");
            table.addHeaderCell("Price");
            table.addHeaderCell("Total");

            double total = 0;

            for (InvoiceItemDTO item : dto.getItems()) {

                double line = item.getQuantity() * item.getUnitPrice();
                total += line;

                table.addCell(String.valueOf(item.getProductId())); // bạn có thể đổi sang tên sản phẩm
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(formatter.format(item.getUnitPrice())); // FIX
                table.addCell(formatter.format(line));                // FIX
            }

            document.add(table);

            document.add(new Paragraph(" "));

            // ===== TOTAL =====
            Paragraph totalText = new Paragraph("TOTAL: " + formatter.format(total))
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT);

            document.add(totalText);

            // ===== FOOTER =====
            document.add(new Paragraph(" "));
            document.add(new Paragraph("cam on quy khach!")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setItalic());

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

