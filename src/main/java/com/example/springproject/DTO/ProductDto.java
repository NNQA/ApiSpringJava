package com.example.springproject.DTO;

import com.example.springproject.models.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductDto {
    private long id;

    private String name;
    private String decription;
    private Double price;

    @JsonIgnore
    private Category category;

    public ProductDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductDto(long id, String name, String decription, Double price, Category category) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.price = price;
        this.category = category;
    }
}
