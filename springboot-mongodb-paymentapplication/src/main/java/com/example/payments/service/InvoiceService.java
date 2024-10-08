package com.example.payments.service;
import com.example.payments.model.InvoiceItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.example.payments.model.Invoice;
import org.springframework.stereotype.Service;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;


@Service
public class InvoiceService {

    public void generateInvoice(Invoice invoice, String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        // Add a title
        document.add(new Paragraph("INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24)));
        document.add(new Paragraph(" "));

        // Company Details
        document.add(new Paragraph("Company Name: " + invoice.getCompanyDetails().getName()));
        document.add(new Paragraph("Address: " + invoice.getCompanyDetails().getAddress()));
        document.add(new Paragraph("Phone: " + invoice.getCompanyDetails().getPhone()));
        document.add(new Paragraph("Email: " + invoice.getCompanyDetails().getEmail()));
        document.add(new Paragraph(" "));

        // Client Details
        document.add(new Paragraph("Bill To:"));
        document.add(new Paragraph("Client Name: " + invoice.getClientDetails().getName()));
        document.add(new Paragraph("Address: " + invoice.getClientDetails().getAddress()));
        document.add(new Paragraph("Phone: " + invoice.getClientDetails().getPhone()));
        document.add(new Paragraph("Email: " + invoice.getClientDetails().getEmail()));
        document.add(new Paragraph(" "));

        // Invoice and PO Numbers
        document.add(new Paragraph("Invoice No: " + invoice.getInvoiceNo()));
        document.add(new Paragraph("Date: " + invoice.getDate()));
        document.add(new Paragraph("PO No: " + invoice.getPoNo()));
        document.add(new Paragraph(" "));

        // Add a table for the items
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100); // Set table width to 100%
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Table headers
        addTableHeader(table);

        double totalAmount = 0;

        // Adding items to the table
        for (InvoiceItem item : invoice.getItems()) {
            double itemTotal = item.getTotal();
            totalAmount += itemTotal;

            table.addCell(item.getDescription());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(String.valueOf(item.getChargePerDay()));
            table.addCell(String.valueOf(itemTotal));
            table.addCell(String.valueOf(invoice.getTds())); // Adjust as necessary
        }

        document.add(table);

        // Total Amount After TDS
        double totalAfterTds = totalAmount - invoice.getTds();
        document.add(new Paragraph("Total Amount After TDS: " + totalAfterTds, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

        // Amount in Words
        String amountInWords = convertAmountToWords(totalAfterTds);
        document.add(new Paragraph("Amount in Words: " + amountInWords));

        // Signature placeholder
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Signature: ______________________"));
        document.add(new Paragraph(" "));

        // Closing the document
        document.close();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Item", "Quantity", "Charge/Day", "Total", "TDS")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private String convertAmountToWords(double amount) {
        // Implement conversion logic here
        return ""; // Return the amount in words
    }
}