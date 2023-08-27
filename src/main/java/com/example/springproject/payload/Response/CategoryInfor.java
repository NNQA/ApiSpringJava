package com.example.springproject.payload.Response;

import com.example.springproject.models.Category;

import java.util.Set;

public class CategoryInfor {
    private long id;
    private String name;
    private Set<Category> children;

    public CategoryInfor(long id, String name, Set<Category> children) {
        this.id = id;
        this.name = name;
        this.children = children;
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

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }
}
