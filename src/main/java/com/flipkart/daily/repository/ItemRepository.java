package com.flipkart.daily.repository;

import com.flipkart.daily.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);

    boolean existsByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);

    void deleteByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);
}
