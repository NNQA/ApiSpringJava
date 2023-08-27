package com.example.springproject.payload.Request;

public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private String nameCate;

    public ProductRequest(String name, String description, Double price, String nameCate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.nameCate = nameCate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNameCate() {
        return nameCate;
    }

    public void setNameCate(String nameCate) {
        this.nameCate = nameCate;
    }
}
