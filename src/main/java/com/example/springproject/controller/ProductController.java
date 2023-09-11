package com.example.springproject.controller;


import com.example.springproject.models.Product;
import com.example.springproject.payload.Request.ProductRequest;
import com.example.springproject.payload.Response.MessageResponse;
import com.example.springproject.payload.Response.ProductPageResponse;
import com.example.springproject.security.JWT.JWTTokenProvider;
import com.example.springproject.security.service.Iml.ProductService;
import com.example.springproject.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    private final JWTTokenProvider jwtUnit;


    public ProductController(ProductService productService, JWTTokenProvider jwtUnit) {
        this.productService = productService;
        this.jwtUnit = jwtUnit;
    }

    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPPLIER')")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest, HttpServletRequest request) {
        try {
            String token = jwtUnit.getJwtFromCookies(request);
            Long id = jwtUnit.getUserId(token);
            Product product = productService.save(productRequest, id);
            return ResponseEntity.ok(new MessageResponse("Product added successfully!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProduct() {
        try {
            List<Product> list = productService.getAllProduct();
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }

    }
    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getByid(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }

    }
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deteleProduct(id);
            System.out.println();
            return ResponseEntity.ok(new MessageResponse("Product with ID + " + id + " has been deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @DeleteMapping("/deleteAllProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPPLIER')")
    public ResponseEntity<?> deleteAllProduct(HttpServletRequest request) {
        try {
            String token = jwtUnit.getJwtFromCookies(request);
            Long id = jwtUnit.getUserId(token);
            productService.deleteAllProduct(id);
            return ResponseEntity.ok(new MessageResponse("All products has been deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody ProductRequest productRequest) {
        try {
            System.out.println(productRequest.getName() + productRequest.getPrice() + productRequest.getNameCate() +productRequest.getDescription());
            productService.updateProduct(id,productRequest);
            return ResponseEntity.ok(new MessageResponse("Product with ID + " + id + " has been updated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
    @GetMapping("/filterProduct")
    public ResponseEntity<?> getSortedProduct(@RequestParam String nameCate) {
        try {
            System.out.println(nameCate);
            List<Product>  products = productService.filterProducts(nameCate);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/getPaginationAllProduct")
    public ResponseEntity<?> getPaginationAllProduct(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_CURRENT, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue =AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir

    ) {
        try {
            ProductPageResponse productPageResponse = productService.getALlProductPage(pageNo, pageSize, sortBy, sortDir);
            return ResponseEntity.ok( productPageResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }
}

