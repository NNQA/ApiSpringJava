package com.example.springproject.security.service.Iml;

import com.example.springproject.Repository.CategoryRepostitory;
import com.example.springproject.Repository.UserRepository;
import com.example.springproject.models.Category;
import com.example.springproject.models.User;
import com.example.springproject.payload.Request.CategoryRequest;
import com.example.springproject.security.service.Interface.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepostitory categoryRepostitory;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepostitory categoryRepostitory, UserRepository userRepository) {
        this.categoryRepostitory = categoryRepostitory;
        this.userRepository = userRepository;
    }

    @Override
    public Category save(CategoryRequest categoryRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User dont exist or session error"));
        if(categoryRequest.getName() == null || Objects.equals(categoryRequest.getName(), "")) {
            throw new RuntimeException("Category can not be null");
        }
        Category category = new Category();

        category.setName(categoryRequest.getName());
        if(categoryRequest.getParentName() != null && !categoryRequest.getName().isEmpty()) {
            Category parent = categoryRepostitory.findByName(categoryRequest.getParentName())
                    .orElseThrow(() -> new RuntimeException("Dont have parent of category"));
            category.setParent(parent);
            parent.getChildren().add(category);
            categoryRepostitory.save(parent);
        }
        user.getCategory().add(category);
        userRepository.save(user);

        return category;
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
    @Override
    public void Delete(Long id) {

    }
    @Override
    public void DeleteAll() {
        categoryRepostitory.deleteAll();
    }
}
