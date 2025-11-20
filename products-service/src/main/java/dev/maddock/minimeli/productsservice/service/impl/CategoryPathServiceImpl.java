package dev.maddock.minimeli.productsservice.service.impl;

import dev.maddock.minimeli.productsservice.entity.Category;
import dev.maddock.minimeli.productsservice.entity.CategoryPath;
import dev.maddock.minimeli.productsservice.repository.CategoryPathRepository;
import dev.maddock.minimeli.productsservice.repository.CategoryRepository;
import dev.maddock.minimeli.productsservice.service.CategoryPathService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryPathServiceImpl implements CategoryPathService {

    private final CategoryPathRepository categoryPathRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void regenerateCategoryPaths() {
        categoryPathRepository.deleteAllInBatch();

        List<Category> categories = categoryRepository.findAll();
        List<CategoryPath> paths = new ArrayList<>();

        for (Category category : categories) {
            paths.addAll(createCategoryPathsInternal(category));
        }

        categoryPathRepository.saveAll(paths);
    }

    @Override
    public void createCategoryPaths(Category category) {
        List<CategoryPath> paths = createCategoryPathsInternal(category);

        categoryPathRepository.saveAll(paths);
    }

    @Override
    public void deleteCategoryPaths(Category category) {
        categoryPathRepository.deleteAllByDescendant(category);
    }

    private List<CategoryPath> createCategoryPathsInternal(Category category) {
        List<CategoryPath> paths = new ArrayList<>();

        int depth = 0;
        Category current = category;
        while (current != null) {
            CategoryPath path = new CategoryPath();
            path.setAncestor(current);
            path.setDescendant(category);
            path.setDepth(depth);
            paths.add(path);

            current = current.getParentCategory();
            depth++;
        }

        return paths;
    }
}
