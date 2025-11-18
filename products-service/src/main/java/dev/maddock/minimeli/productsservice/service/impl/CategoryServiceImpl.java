package dev.maddock.minimeli.productsservice.service.impl;

import dev.maddock.minimeli.productsservice.dto.category.CategoryDto;
import dev.maddock.minimeli.productsservice.dto.category.CreateCategoryRequest;
import dev.maddock.minimeli.productsservice.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryDto createCategory(CreateCategoryRequest request) {
        return new CategoryDto(UUID.randomUUID(), "title", "desc", null);
    }
}
