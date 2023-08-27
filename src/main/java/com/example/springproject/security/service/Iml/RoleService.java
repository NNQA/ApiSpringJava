package com.example.springproject.security.service.Iml;

import com.example.springproject.Repository.RoleRepository;
import com.example.springproject.models.Erole;
import com.example.springproject.models.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addRoles() {
        Role userRole = new Role();
        userRole.setName(Erole.ROLE_USER);
        roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setName(Erole.ROLE_ADMIN);
        roleRepository.save(adminRole);
    }
}
