package dev.maddock.minimeli.productsservice.service;

import dev.maddock.minimeli.productsservice.dto.category.CategoryDto;
import dev.maddock.minimeli.productsservice.dto.category.CategoryTreeNode;
import dev.maddock.minimeli.productsservice.dto.category.CreateCategoryRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryRequest request);

    void deleteCategory(UUID categoryId);

    CategoryDto getCategoryById(UUID categoryId);

    List<CategoryTreeNode> getAllCategoriesAsTree();

    List<CategoryDto> getAllCategories();

    CategoryDto moveCategory(UUID categoryId, UUID newParentId);

}
