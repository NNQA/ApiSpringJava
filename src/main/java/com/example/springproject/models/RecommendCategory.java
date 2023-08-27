package com.example.springproject.models;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(
        name = "RecommendCate"
)
public class RecommendCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    @CollectionTable(name = "category_values", joinColumns = @JoinColumn(name = "recommendation_id"))
    @MapKeyColumn(name = "category_id")
    @Column(name = "value")
    private Map<Long, Integer> categoryValues = new HashMap<>();


    public RecommendCategory(Long id, User user, Map<Long, Integer> categoryValues) {
        Id = id;
        this.user = user;
        this.categoryValues = categoryValues;
    }

    public RecommendCategory(User user, Map<Long, Integer> categoryValues) {
        this.user = user;
        this.categoryValues = categoryValues;
    }

    public RecommendCategory() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Long, Integer> getCategoryValues() {
        return categoryValues;
    }

    public void setCategoryValues(Map<Long, Integer> categoryValues) {
        this.categoryValues = categoryValues;
    }
}
