package com.flipkart.daily.dto;

public class ItemResponse {
    private String brand;
    private String category;
    private int price;
    private int quantity;

    public ItemResponse(String brand, String category, int price, int quantity) {
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public String getBrand() { return brand; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
