package com.example.payments.model;

public class InvoiceItem {
    private String description; // Description of the item
    private int quantity; // Quantity of the item
    private double chargePerDay; // Charge per day for the item

    // Calculate total for the item
    public double getTotal() {
        return quantity * chargePerDay;
    }

    // Getters and Setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getChargePerDay() {
        return chargePerDay;
    }

    public void setChargePerDay(double chargePerDay) {
        this.chargePerDay = chargePerDay;
    }
}
