package com.example.storemanagement.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    @Column(nullable = false)
    private String categoryName;

    // Constructor không đối số (bắt buộc cho JPA)
    public Category() {
    }

    // Constructor có đối số
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getter và Setter
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
