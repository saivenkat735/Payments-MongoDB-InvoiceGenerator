package com.example.payments.controller;

import com.example.payments.model.Invoice;
import com.example.payments.service.InvoiceService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateInvoice(@RequestBody Invoice invoice) {
        String directoryPath = "invoices/"; // Relative path where you want to save the invoices
        String fileName = "invoice_" + invoice.getInvoiceNo() + ".pdf";
        String filePath = directoryPath + File.separator + fileName;

        // Create the directory if it doesn't exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        } // Set your path here
        try {
            invoiceService.generateInvoice(invoice, filePath);
            return ResponseEntity.ok("Invoice generated successfully!");
        } catch (IOException | DocumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating invoice: " + e.getMessage());
        }
    }

}
