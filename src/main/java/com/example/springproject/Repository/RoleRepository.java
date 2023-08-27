package com.example.springproject.Repository;

import com.example.springproject.models.Erole;
import com.example.springproject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Erole name);
}
