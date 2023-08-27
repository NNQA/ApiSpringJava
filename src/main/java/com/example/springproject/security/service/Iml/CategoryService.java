package com.example.springproject.security.service.Iml;

import com.example.springproject.Repository.CategoryRepostitory;
import com.example.springproject.models.Category;
import com.example.springproject.payload.Request.CategoryRequest;
import com.example.springproject.security.service.Interface.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepostitory categoryRepostitory;

    public CategoryService(CategoryRepostitory categoryRepostitory) {
        this.categoryRepostitory = categoryRepostitory;
    }

    @Override
    public Category save(CategoryRequest categoryRequest) {
        Category category = new Category();

        category.setName(categoryRequest.getName());
        if(categoryRequest.getParentName() != null && !categoryRequest.getName().isEmpty()) {
            Category parent = categoryRepostitory.findByName(categoryRequest.getParentName())
                    .orElseThrow(() -> new RuntimeException("Dont have parent of category"));
            category.setParent(parent);
            parent.getChildren().add(category);
            categoryRepostitory.save(parent);
        }
        return categoryRepostitory.save(category);
    }
    @Override
    public List<Category> GetAllChildrenOfParent(String name) {
        Category parent = categoryRepostitory.findByName(name)
                .orElseThrow(() -> new RuntimeException("Dont have parent of category"));
        return new ArrayList<>(parent.getChildren());
    }

    @Override
    public Category getId(Long id) {
        Optional<Category> category = categoryRepostitory.findById(id);
        return  category
                .orElseThrow(() -> new RuntimeException("Category not found witd Id: " + id));
    }
}
