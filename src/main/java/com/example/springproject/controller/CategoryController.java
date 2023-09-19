package com.example.springproject.controller;

import com.example.springproject.models.Category;
import com.example.springproject.payload.Request.CategoryRequest;
import com.example.springproject.payload.Response.MessageResponse;
import com.example.springproject.security.JWT.JWTTokenProvider;
import com.example.springproject.security.service.Iml.CategoryService;
import com.example.springproject.security.service.Iml.RecommendSystemCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final RecommendSystemCategoryService recommendSystemCategoryService;
    private final JWTTokenProvider jwtUtils;

    public CategoryController(CategoryService categoryService, RecommendSystemCategoryService recommendSystemCategoryService, JWTTokenProvider jwtUtils) {
        this.categoryService = categoryService;
        this.recommendSystemCategoryService = recommendSystemCategoryService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/addCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody @Valid CategoryRequest categoryRequest, HttpServletRequest httpServletRequest) {
        try {
            categoryService.save(categoryRequest);
            return ResponseEntity.ok(new MessageResponse("Category added successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @GetMapping("/getAllCategory")
    public ResponseEntity<?> getAllCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            List<Category> category =  categoryService.GetAllCategory();
            return ResponseEntity.ok(category);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed: " + e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.Delete(id);
            return ResponseEntity.ok("Deletion successful");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed: " + e.getMessage());
        }
    }
}
