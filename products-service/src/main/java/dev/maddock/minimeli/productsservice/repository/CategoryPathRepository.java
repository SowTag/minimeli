package dev.maddock.minimeli.productsservice.repository;

import dev.maddock.minimeli.productsservice.entity.Category;
import dev.maddock.minimeli.productsservice.entity.CategoryPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryPathRepository extends JpaRepository<CategoryPath, UUID> {
    void deleteAllByDescendant(Category descendant);
}