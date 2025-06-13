package com.flipkart.daily.repository;

import com.flipkart.daily.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // Filtering + Pagination
    Page<Item> findByBrandIgnoreCaseContainingAndCategoryIgnoreCaseContaining(
            String brand, String category, Pageable pageable
    );

    Optional<Item> findByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);

    boolean existsByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);

    void deleteByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);
}
