package com.example.springproject.security.service.Interface;

import com.example.springproject.models.Product;
import com.example.springproject.payload.Request.ProductRequest;
import com.example.springproject.payload.Response.ProductPageResponse;

import java.util.List;

public interface IProductService {

    Product save(ProductRequest productRequest, Long id);

    List<Product> getAllProduct();
    Product getByid(Long productId);
    String updateProduct(Long id,ProductRequest productRequest, Long productId);

    void deteleProduct(Long productId);

    void deleteAllProduct(Long id);

    List<Product> filterProducts(String cateName);

    Product ApproveProduct(Long id);
    String RejectProduct(Long userid, Long productId);

    ProductPageResponse getALlProductPage(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

}
