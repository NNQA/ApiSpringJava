package com.example.springproject.security.service.Interface;

import com.example.springproject.models.Product;
import com.example.springproject.payload.Request.ProductRequest;
import com.example.springproject.payload.Response.ProductPageResponse;

import java.util.List;

public interface IProductService {
    Product save(ProductRequest productRequest);
    List<Product> getAllProduct();
    Product getByid(Long productId);
    Product updateProduct(Long productId,ProductRequest productRequest);

    void deteleProduct(Long productId);

    List<Product> filterProducts(String cateName);

    ProductPageResponse getALlProductPage(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

}
