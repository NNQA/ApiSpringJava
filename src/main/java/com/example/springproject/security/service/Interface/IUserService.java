package com.example.springproject.security.service.Interface;

import com.example.springproject.models.User;
import com.example.springproject.payload.Request.SignupRequest;

public interface IUserService {
    User save(SignupRequest signupRequest);
    void deleteAll();
}
