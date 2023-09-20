package com.example.springproject.DTO;

import com.example.springproject.models.Product;


public class OrderItemDTO {

    private String nameProduct;
    private Long quantity;

    public OrderItemDTO(String nameProduct, Long quantity) {
        this.nameProduct = nameProduct;
        this.quantity = quantity;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
