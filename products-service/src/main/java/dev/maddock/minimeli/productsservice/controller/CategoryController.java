package dev.maddock.minimeli.productsservice.controller;

import dev.maddock.minimeli.productsservice.dto.category.CategoryDto;
import dev.maddock.minimeli.productsservice.dto.category.CategoryTreeNode;
import dev.maddock.minimeli.productsservice.dto.category.CreateCategoryRequest;
import dev.maddock.minimeli.productsservice.service.CategoryPathService;
import dev.maddock.minimeli.productsservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
class CategoryController {
    private final CategoryService categoryService;
    private final CategoryPathService categoryPathService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/tree")
    public ResponseEntity<List<CategoryTreeNode>> getAllCategoriesAsTree() {
        return ResponseEntity.ok(categoryService.getAllCategoriesAsTree());
    }

    @PostMapping("/tree/regenerate")
    public ResponseEntity<Void> regenerateClosureTable() {
        categoryPathService.regenerateCategoryPaths();
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CreateCategoryRequest request) {
        CategoryDto createdCategory = categoryService.createCategory(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategory.id())
                .toUri();

        return ResponseEntity.created(location).body(createdCategory);
    }
}
