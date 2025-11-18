package dev.maddock.minimeli.productsservice.service;

import dev.maddock.minimeli.productsservice.dto.category.CategoryDto;
import dev.maddock.minimeli.productsservice.dto.category.CreateCategoryRequest;

import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryRequest request);

    CategoryDto moveCategory(UUID id, UUID newParentId);

    CategoryDto getCategoryById(UUID id);
}
