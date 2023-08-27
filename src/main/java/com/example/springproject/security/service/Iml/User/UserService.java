package com.example.springproject.security.service.Iml.User;

import com.example.springproject.Repository.RoleRepository;
import com.example.springproject.Repository.UserRepository;
import com.example.springproject.models.Erole;
import com.example.springproject.models.Role;
import com.example.springproject.models.User;
import com.example.springproject.payload.Request.SignupRequest;
import com.example.springproject.security.service.Interface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public User save(SignupRequest signupRequest) {
        if(userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new  IllegalArgumentException("Username already exists");
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        Set<String> str = signupRequest.getRole();
        Set<Role> roles = new HashSet<Role>();

        if (str == null) {
            Role userRole = roleRepository.findByName(Erole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            System.out.println("null" + userRole);
            roles.add(userRole);
        } else {
            str.forEach(role -> {
                switch (role) {
                    case "admin":
                        System.out.println("admind" + role);
                        Role adminRole = roleRepository.findByName(Erole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                        roles.add(adminRole);
                        break;
                    default:
                        System.out.println("user" + role);
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
}
