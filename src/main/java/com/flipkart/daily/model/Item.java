package com.flipkart.daily.model;

public class Item {
    private String brand;
    private String category;
    private int price;
    private int quantity;

    // Getters & Setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Optional: override toString
    @Override
    public String toString() {
        return brand + " - " + category + " : ₹" + price + " (Qty: " + quantity + ")";
    }
}
