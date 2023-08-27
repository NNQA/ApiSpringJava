package com.example.springproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.security.PrivateKey;

@Entity
@Table(
        name = "product"
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String decription;
    private Double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private Category category;

    public Product(long id, String name, String decription, Double price, Category category) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.price = price;
        this.category = category;
    }

    public Product(String name, String decription, Double price) {
        this.name = name;
        this.decription = decription;
        this.price = price;
    }

    public Product() {

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
}
