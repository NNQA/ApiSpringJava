package com.example.springproject.payload.Response;

import com.example.springproject.DTO.ProductDto;
import com.example.springproject.models.Product;

import java.util.List;

public class ProductPageResponse {

    private List<ProductDto> products;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;

    private boolean last;

    public ProductPageResponse() {
    }

    public ProductPageResponse(List<ProductDto> products, int pageNo, int pageSize, Long totalElements, int totalPages, boolean last) {
        this.products = products;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
