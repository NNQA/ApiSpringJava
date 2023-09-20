package com.example.springproject.models;


import javax.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Oder order;

    public Oder getOrder() {
        return order;
    }

    public void setOrder(Oder order) {
        this.order = order;
    }

    public OrderItem() {
    }

    public OrderItem(Long id, Product product, int quantity, Oder order) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
