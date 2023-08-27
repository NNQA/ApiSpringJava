package com.example.springproject.Repository;

import com.example.springproject.models.RecommendCategory;
import com.example.springproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableJpaRepositories
public interface RecommendSystemCategoryRepository extends JpaRepository<RecommendCategory, Long> {
    RecommendCategory findByUser(User user);
}
