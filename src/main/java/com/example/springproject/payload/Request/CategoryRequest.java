package com.example.springproject.payload.Request;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

public class CategoryRequest {
    private String parentName;
    @NotBlank
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
