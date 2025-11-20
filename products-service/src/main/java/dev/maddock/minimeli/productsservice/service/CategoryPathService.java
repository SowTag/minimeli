package dev.maddock.minimeli.productsservice.service;

import dev.maddock.minimeli.productsservice.entity.Category;

public interface CategoryPathService {
    void regenerateCategoryPaths();

    void createCategoryPaths(Category category);

    void deleteCategoryPaths(Category category);
}
