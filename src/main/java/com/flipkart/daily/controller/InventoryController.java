package com.flipkart.daily.controller;

import com.flipkart.daily.model.Item;
import com.flipkart.daily.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.flipkart.daily.dto.AddItemRequest;
import com.flipkart.daily.dto.ItemResponse;
import com.flipkart.daily.dto.UpdateItemRequest;


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

    @GetMapping("/search")
    public List<ItemResponse> searchItems(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String sortBy
    ) {
        return service.searchItems(brand, category, minPrice, maxPrice, sortBy).stream()
                .map(item -> new ItemResponse(
                        item.getBrand(),
                        item.getCategory(),
                        item.getPrice(),
                        item.getQuantity()
                )).toList();
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
