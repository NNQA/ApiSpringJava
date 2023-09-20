package com.example.springproject.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Oder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private boolean checkout = false;

    public boolean isCheckout() {
        return checkout;
    }

    public void setCheckout(boolean checkout) {
        this.checkout = checkout;
    }

    public Oder(User user, List<OrderItem> orderItems) {
        this.user = user;
        this.orderItems = orderItems;
    }


    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Oder(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Oder() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Oder addItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        return this;
    }
    public Oder addUser(User user) {
        this.setUser(user);
        return this;
    }
}
