package com.example.springproject.security.service.Iml.User;

import com.example.springproject.Repository.UserRepository;
import com.example.springproject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsImplementService implements UserDetailsService {
     private final UserRepository userRepository;

    public UserDetailsImplementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow( () -> new UsernameNotFoundException("User not found with userName or email" + usernameOrEmail));
        return com.example.springproject.security.service.Iml.User.UserDetails.build(user);
    }
}
