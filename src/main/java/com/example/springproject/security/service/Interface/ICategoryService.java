package com.example.springproject.security.service.Interface;

import com.example.springproject.models.Category;
import com.example.springproject.payload.Request.CategoryRequest;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Category save(CategoryRequest categoryRequest, Long userId);
    List<Category> GetAllChildrenOfParent(String name);
    Category getId(Long id);
    void Delete(Long id);
    void DeleteAll();

}

