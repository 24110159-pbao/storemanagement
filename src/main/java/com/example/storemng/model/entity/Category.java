package com.example.storemng.model.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public Category() {}

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryID() { return categoryID; }
    public String getCategoryName() { return categoryName; }
    public List<Product> getProducts() { return products; }

    public void setCategoryID(Long categoryID) { this.categoryID = categoryID; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setProducts(List<Product> products) { this.products = products; }

    @Override
    public String toString() {
        return categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category that)) return false;
        return categoryID != null && categoryID.equals(that.categoryID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}