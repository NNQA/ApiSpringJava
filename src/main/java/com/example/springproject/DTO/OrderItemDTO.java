package com.example.springproject.DTO;

import com.example.springproject.models.Product;


public class OrderItemDTO {

    private String nameProduct;
    private int quantity;

    public OrderItemDTO(String nameProduct, int quantity) {
        this.nameProduct = nameProduct;
        this.quantity = quantity;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
