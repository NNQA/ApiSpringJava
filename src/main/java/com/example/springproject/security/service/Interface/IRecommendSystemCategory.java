package com.example.springproject.security.service.Interface;

import com.example.springproject.models.Category;

import java.util.List;

public interface IRecommendSystemCategory {
    void fillCategorys(String nameUser, Category category);
    List<Category> popularCate(String nameUser);
}
