package com.example.springproject.Repository;

import com.example.springproject.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
