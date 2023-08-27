package com.example.springproject.Repository;

import com.example.springproject.models.Category;
import com.example.springproject.models.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    @NotNull
    Optional<Product> findById(Long aLong);
    Optional<Product> findByName(String name);
    List<Product> findAllByCategoryOrderByPriceDesc(Category category);
}
