package com.example.springproject.security.service.Iml.User;

import com.example.springproject.Repository.RecommendSystemCategoryRepository;
import com.example.springproject.Repository.RoleRepository;
import com.example.springproject.Repository.UserRepository;
import com.example.springproject.models.Erole;
import com.example.springproject.models.RecommendCategory;
import com.example.springproject.models.Role;
import com.example.springproject.models.User;
import com.example.springproject.payload.Request.SignupRequest;
import com.example.springproject.security.service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RecommendSystemCategoryRepository recommendSystemCategoryRepository;
    @Autowired
    private  PasswordEncoder encoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, RecommendSystemCategoryRepository recommendSystemCategoryRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.recommendSystemCategoryRepository = recommendSystemCategoryRepository;
    }

    @Override
    public User save(SignupRequest signupRequest) {
        if(userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new  IllegalArgumentException("Username already exists");
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Set<String> str = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (str == null) {
            Role userRole = roleRepository.findByName(Erole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            System.out.println("null" + userRole);
            roles.add(userRole);
        } else {
            str.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(Erole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "supplier":
                        Role supplierRole = roleRepository.findByName(Erole.ROLE_SUPPLIER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(supplierRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(Erole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        User user = new User(
                signupRequest.getUsername(), signupRequest.getEmail(),encoder.encode(signupRequest.getPassword())
        );
        user.setRoles(roles);
        return userRepository.save(user);
    }
    @Override
    public void deleteAll() {
        List<RecommendCategory> recommendCategoryList = recommendSystemCategoryRepository.findAll();
        for (RecommendCategory recommendCategory :  recommendCategoryList) {
            recommendCategory.setUser(null);
            recommendSystemCategoryRepository.save(recommendCategory);
        }
        recommendSystemCategoryRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
