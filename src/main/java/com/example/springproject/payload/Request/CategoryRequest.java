package com.example.springproject.payload.Request;

public class CategoryRequest {
    private String parentName;
    private String name;

    public CategoryRequest(String parentName, String name) {
        this.parentName = parentName;
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
