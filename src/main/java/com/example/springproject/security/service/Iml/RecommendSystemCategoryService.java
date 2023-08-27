package com.example.springproject.security.service.Iml;

import com.example.springproject.Repository.CategoryRepostitory;
import com.example.springproject.Repository.RecommendSystemCategoryRepository;
import com.example.springproject.Repository.UserRepository;
import com.example.springproject.models.Category;
import com.example.springproject.models.RecommendCategory;
import com.example.springproject.models.User;
import com.example.springproject.security.service.Interface.IRecommendSystemCategory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendSystemCategoryService implements IRecommendSystemCategory {
    private final UserRepository userRepository;

    private final RecommendSystemCategoryRepository recommendSystemCategoryRepository;

    private final CategoryRepostitory categoryRepostitory;

    public RecommendSystemCategoryService(UserRepository userRepository, RecommendSystemCategoryRepository recommendSystemCategoryRepository, CategoryRepostitory categoryRepostitory) {
        this.userRepository = userRepository;
        this.recommendSystemCategoryRepository = recommendSystemCategoryRepository;
        this.categoryRepostitory = categoryRepostitory;
    }

    @Override
    public void fillCategorys(String nameUser, Category category) {
        Optional<User> user = userRepository.findByUsername(nameUser);

        RecommendCategory recommendCategory = recommendSystemCategoryRepository.findByUser(user.get());

        if(recommendCategory == null) {
            HashMap<Long, Integer> map = new HashMap<>();
            map.put(category.getId(), 0);
            recommendCategory = new RecommendCategory (
                    user.get() ,
                    map
                    );
            recommendSystemCategoryRepository.save(recommendCategory);
        } else {

            Map<Long, Integer> map = recommendCategory.getCategoryValues();
            if (!map.containsKey(
                    category.getId()
            )) {
                recommendCategory.getCategoryValues().put(category.getId(), 0);
                recommendSystemCategoryRepository.save(recommendCategory);
            } else {
                recommendCategory.getCategoryValues().replace(category.getId(),
                        recommendCategory.getCategoryValues().get(category.getId()) + 1);
                recommendSystemCategoryRepository.save(recommendCategory);
            }

        }
    }

    @Override
    public List<Category> popularCate(String nameUser) {
        Optional<User> user = userRepository.findByUsername(nameUser);
        RecommendCategory recommendCategory = recommendSystemCategoryRepository.findByUser(user.get());
        if(recommendCategory != null) {
            List<Category> categories = new ArrayList<>();
            for(Map.Entry<Long, Integer> map: recommendCategory.getCategoryValues().entrySet()) {
                if(map.getValue() > 1) {
                    Optional<Category> category = categoryRepostitory.findById(map.getKey());
                    categories.add(category.get());
                }
            }
            return categories;
        }
        return null;
    }
}
