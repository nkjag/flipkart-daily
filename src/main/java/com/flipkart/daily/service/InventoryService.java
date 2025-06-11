package com.flipkart.daily.service;

import com.flipkart.daily.exception.ItemNotFoundException;
import com.flipkart.daily.model.Item;
import com.flipkart.daily.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final ItemRepository repository;

    public InventoryService(ItemRepository repository) {
        this.repository = repository;
    }

    public void addItem(Item item) {
        boolean exists = repository.existsByBrandIgnoreCaseAndCategoryIgnoreCase(item.getBrand(), item.getCategory());
        if (!exists) {
            repository.save(item);
        }
    }

    public void addInventory(String brand, String category, int quantity) {
        Item item = repository.findByBrandIgnoreCaseAndCategoryIgnoreCase(brand, category)
                .orElseThrow(() -> new ItemNotFoundException("Item not found in inventory: " + brand + " - " + category));
        item.setQuantity(item.getQuantity() + quantity);
        repository.save(item);
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    public List<Item> searchItems(String brand, String category, Integer minPrice, Integer maxPrice, String sortBy) {
        return repository.findAll().stream()
                .filter(item -> brand == null || item.getBrand().equalsIgnoreCase(brand))
                .filter(item -> category == null || item.getCategory().equalsIgnoreCase(category))
                .filter(item -> minPrice == null || item.getPrice() >= minPrice)
                .filter(item -> maxPrice == null || item.getPrice() <= maxPrice)
                .sorted(getComparator(sortBy))
                .collect(Collectors.toList());
    }

    public void updateItem(String brand, String category, int price, int quantity) {
        Item item = repository.findByBrandIgnoreCaseAndCategoryIgnoreCase(brand, category)
                .orElseThrow(() -> new ItemNotFoundException("Item not found for update: " + brand + " - " + category));
        item.setPrice(price);
        item.setQuantity(quantity);
        repository.save(item);
    }

    public void deleteItem(String brand, String category) {
        boolean exists = repository.existsByBrandIgnoreCaseAndCategoryIgnoreCase(brand, category);
        if (!exists) {
            throw new ItemNotFoundException("Item not found for delete: " + brand + " - " + category);
        }
        repository.deleteByBrandIgnoreCaseAndCategoryIgnoreCase(brand, category);
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
}
