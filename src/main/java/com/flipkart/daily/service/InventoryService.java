package com.flipkart.daily.service;

import com.flipkart.daily.dto.ItemResponse;
import com.flipkart.daily.exception.ItemNotFoundException;
import com.flipkart.daily.model.Item;
import com.flipkart.daily.repository.ItemRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

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

    // ✅ Updated: JPA-based filtered search with pagination and sorting
    public Page<ItemResponse> searchItems(
            String brand,
            String category,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        String brandFilter = brand != null ? brand : "";
        String categoryFilter = category != null ? category : "";

        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy != null ? sortBy : "price"); // default sort = price asc
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Item> itemsPage = repository.findByBrandIgnoreCaseContainingAndCategoryIgnoreCaseContaining(
                brandFilter, categoryFilter, pageable
        );

        return itemsPage.map(toItemResponse());
    }

    private Function<Item, ItemResponse> toItemResponse() {
        return item -> new ItemResponse(
                item.getBrand(),
                item.getCategory(),
                item.getPrice(),
                item.getQuantity()
        );
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
    public Page<Item> getPagedItems(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
