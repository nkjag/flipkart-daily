package com.flipkart.daily.controller;

import com.flipkart.daily.model.Item;
import com.flipkart.daily.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.flipkart.daily.dto.AddItemRequest;
import com.flipkart.daily.dto.ItemResponse;
import com.flipkart.daily.dto.UpdateItemRequest;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @PostMapping("/add-item")
    public String addItem(@Valid @RequestBody AddItemRequest request) {
        Item item = new Item();
        item.setBrand(request.getBrand());
        item.setCategory(request.getCategory());
        item.setPrice(request.getPrice());
        item.setQuantity(request.getQuantity());
        service.addItem(item);
        return "Item added successfully";
    }

    @PostMapping("/add-stock")
    public String addInventory(@RequestParam String brand,
                               @RequestParam String category,
                               @RequestParam int quantity) {
        service.addInventory(brand, category, quantity);
        return "Stock updated";
    }

    @GetMapping("/all")
    public List<ItemResponse> getAllItems() {
        return service.getAllItems().stream()
                .map(item -> new ItemResponse(
                        item.getBrand(),
                        item.getCategory(),
                        item.getPrice(),
                        item.getQuantity()
                )).toList();
    }

    @Operation(
            summary = "Search items with filters, sorting, and pagination",
            description = "You can search items by brand and category (case-insensitive), and apply pagination and sorting using query params."
    )
    @GetMapping("/search")
    public Page<ItemResponse> searchItems(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return service.searchItems(brand, category, page, size, sortBy, sortDir);
    }
    @Operation(
            summary = "Paged listing using Spring Pageable (tests global defaults)",
            description = "Returns all items using Pageable injection. Useful for testing global config from application.properties."
    )
    @GetMapping("/paged")
    public Page<Item> getPagedItems(Pageable pageable) {
        return service.getPagedItems(pageable);
    }
    @PutMapping("/update-item")
    public String updateItem(@Valid @RequestBody UpdateItemRequest request) {
        service.updateItem(request.getBrand(), request.getCategory(), request.getPrice(), request.getQuantity());
        return "Item updated successfully";
    }

    @DeleteMapping("/delete-item")
    public String deleteItem(
            @RequestParam String brand,
            @RequestParam String category) {
        service.deleteItem(brand, category);
        return "Item deleted successfully";
    }

}
