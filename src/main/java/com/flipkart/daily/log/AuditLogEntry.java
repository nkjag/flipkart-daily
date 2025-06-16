package com.flipkart.daily.log;

import java.time.LocalDateTime;

public class AuditLogEntry {

    private String action;     // ADD, UPDATE, DELETE, IMPORT
    private String brand;
    private String category;
    private int price;
    private int quantity;
    private LocalDateTime timestamp;

    public AuditLogEntry(String action, String brand, String category, int price, int quantity) {
        this.action = action;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] ACTION=" + action.toUpperCase() +
                ", BRAND=" + brand +
                ", CATEGORY=" + category +
                ", PRICE=" + price +
                ", QUANTITY=" + quantity;
    }
}
