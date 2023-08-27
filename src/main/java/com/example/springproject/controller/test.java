package com.example.springproject.controller;

import com.example.springproject.models.Category;
import com.example.springproject.models.User;
import com.example.springproject.payload.Response.MessageResponse;
import com.example.springproject.security.JWT.JWTTokenProvider;
import com.example.springproject.security.service.Iml.RecommendSystemCategoryService;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class test {

    private final JWTTokenProvider jwtUtils;
    private final RecommendSystemCategoryService recommendSystemCategoryService;

    public test(JWTTokenProvider jwtUtils, RecommendSystemCategoryService recommendSystemCategoryService) {
        this.jwtUtils = jwtUtils;
        this.recommendSystemCategoryService = recommendSystemCategoryService;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userAccess() {
        return "user Content.";
    }

    @GetMapping("/SuggestionItemCate")
    public ResponseEntity<?> getListSuggestion (HttpServletRequest request) {
        String token = jwtUtils.getJwtFromCookies(request);
        String userName = jwtUtils.getUserNameFromJwtToken(token);

        System.out.println("name" + userName);
        List<Category> categories = recommendSystemCategoryService.popularCate(userName);

        if(categories.isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("No recent categories"));
        }
        return  ResponseEntity.ok(categories);
    }
}
