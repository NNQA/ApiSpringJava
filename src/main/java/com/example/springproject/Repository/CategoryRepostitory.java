package com.example.springproject.Repository;

import com.example.springproject.models.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@EnableJpaRepositories
public interface CategoryRepostitory extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    @NotNull List<Category> findAll();
}
