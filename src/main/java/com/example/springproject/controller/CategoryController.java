package com.example.springproject.controller;

import com.example.springproject.models.Category;
import com.example.springproject.models.RecommendCategory;
import com.example.springproject.payload.Request.CategoryRequest;
import com.example.springproject.payload.Request.SignupRequest;
import com.example.springproject.payload.Response.MessageResponse;
import com.example.springproject.payload.Response.UserInfoResponse;
import com.example.springproject.security.JWT.JWTTokenProvider;
import com.example.springproject.security.service.Iml.CategoryService;
import com.example.springproject.security.service.Iml.RecommendSystemCategoryService;
import com.example.springproject.security.service.Iml.User.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @Autowired
    private RecommendSystemCategoryService recommendSystemCategoryService;
    @Autowired
    private JWTTokenProvider jwtUtils;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/addCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPPLIER')")
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryRequest categoryRequest, HttpServletRequest httpServletRequest) {
        try {
            String token = jwtUtils.getJwtFromCookies(httpServletRequest);
            Long id = jwtUtils.getUserId(token);
            categoryService.save(categoryRequest, id);
            return ResponseEntity.ok(new MessageResponse("Category added successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @GetMapping("/getAllCategory")
    public ResponseEntity<?> getAllCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            List<Category> category =  categoryService.GetAllChildrenOfParent(categoryRequest.getParentName());
            List<String> name = new ArrayList<>();
            category.forEach(category1 -> {
                name.add(category1.getName());
            });
            return ResponseEntity.ok(name);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @GetMapping("/getCategory/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id, HttpServletRequest request) {
        try {
            String token = jwtUtils.getJwtFromCookies(request);
            String name = jwtUtils.getUserNameFromJwtToken(token);
            Category category = categoryService.getId(id);
            recommendSystemCategoryService.fillCategorys(name, category);
                return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteAll() {
        try {
            categoryService.DeleteAll();
            return ResponseEntity.ok("Deletion successful");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed: " + e.getMessage());
        }
    }
}
