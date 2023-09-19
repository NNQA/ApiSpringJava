package com.example.springproject.Repository;

import com.example.springproject.models.Oder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Oder, Long> {
}
