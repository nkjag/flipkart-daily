package com.flipkart.daily.service;

import com.flipkart.daily.exception.ItemNotFoundException;
import com.flipkart.daily.model.Item;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final Map<String, Item> inventory = new HashMap<>();

    private String getKey(String brand, String category) {
        return brand.toLowerCase() + "::" + category.toLowerCase();
    }

    public void addItem(Item item) {
        String key = getKey(item.getBrand(), item.getCategory());
        inventory.putIfAbsent(key, item);
    }

    public void addInventory(String brand, String category, int quantity) {
        String key = getKey(brand, category);
        Item item = inventory.get(key);
        if (item == null) {
            throw new RuntimeException("Item not found in inventory.");
        }
        item.setQuantity(item.getQuantity() + quantity);
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(inventory.values());
    }

    // ✅ New: Search with filters and sorting
    public List<Item> searchItems(String brand, String category, Integer minPrice, Integer maxPrice, String sortBy) {
        return inventory.values().stream()
                .filter(item -> brand == null || item.getBrand().equalsIgnoreCase(brand))
                .filter(item -> category == null || item.getCategory().equalsIgnoreCase(category))
                .filter(item -> minPrice == null || item.getPrice() >= minPrice)
                .filter(item -> maxPrice == null || item.getPrice() <= maxPrice)
                .sorted(getComparator(sortBy))
                .collect(Collectors.toList());
    }

    private Comparator<Item> getComparator(String sortBy) {
        if (sortBy == null) return Comparator.comparingInt(Item::getPrice); // default

        return switch (sortBy.toLowerCase()) {
            case "price_desc" -> Comparator.comparingInt(Item::getPrice).reversed();
            case "quantity_asc" -> Comparator.comparingInt(Item::getQuantity);
            case "quantity_desc" -> Comparator.comparingInt(Item::getQuantity).reversed();
            default -> Comparator.comparingInt(Item::getPrice); // default: price_asc
        };
    }
    public void updateItem(String brand, String category, int price, int quantity) {
        String key = getKey(brand, category);
        Item item = inventory.get(key);
        if (item == null) {
            throw new ItemNotFoundException("Item not found for update: " + brand + " - " + category);
        }
        item.setPrice(price);
        item.setQuantity(quantity);
    }

    public void deleteItem(String brand, String category) {
        String key = getKey(brand, category);
        Item removed = inventory.remove(key);
        if (removed == null) {
            throw new ItemNotFoundException("Item not found for delete: " + brand + " - " + category);
        }
    }


}

