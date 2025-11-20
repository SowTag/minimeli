package dev.maddock.minimeli.productsservice.service.impl;

import dev.maddock.minimeli.productsservice.dto.category.CategoryDto;
import dev.maddock.minimeli.productsservice.dto.category.CategoryTreeNode;
import dev.maddock.minimeli.productsservice.dto.category.CreateCategoryRequest;
import dev.maddock.minimeli.productsservice.entity.Category;
import dev.maddock.minimeli.productsservice.exception.CategoryNotFoundException;
import dev.maddock.minimeli.productsservice.repository.CategoryRepository;
import dev.maddock.minimeli.productsservice.service.CategoryPathService;
import dev.maddock.minimeli.productsservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryPathService categoryPathService;

    @Override
    @Transactional
    public CategoryDto createCategory(CreateCategoryRequest request) {
        var newCategoryBuilder = Category.builder()
                .title(request.title())
                .description(request.description());

        if (request.parentCategoryId() != null) {
            Category parentCategory = getCategoryEntityById(request.parentCategoryId());

            newCategoryBuilder.parentCategory(parentCategory);
        }

        Category savedCategory = categoryRepository.save(newCategoryBuilder.build());

        categoryPathService.createCategoryPaths(savedCategory);

        return CategoryDto.from(savedCategory);

    }

    private Category getCategoryEntityById(UUID categoryId) {
        return categoryRepository.findCategoryById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        // TODO: Implement
    }

    @Override
    public CategoryDto getCategoryById(UUID categoryId) {
        return CategoryDto.from(getCategoryEntityById(categoryId));
    }

    @Override
    public List<CategoryTreeNode> getAllCategoriesAsTree() {
        return List.of();
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        // It doesn't make much sense to paginate this since getting categories as a tree already gets all categories
        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(CategoryDto::from).toList();
    }

    @Override
    public CategoryDto moveCategory(UUID categoryId, UUID newParentId) {
        // TODO: Implement
        return null;
    }
}
