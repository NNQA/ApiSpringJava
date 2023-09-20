package com.example.springproject.Repository;

import com.example.springproject.models.Oder;
import com.example.springproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Oder, Long> {
    Optional<List<Oder>> findAllByUser(User user);
}
