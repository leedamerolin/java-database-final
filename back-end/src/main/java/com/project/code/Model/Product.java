package com.project.code.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(
    name = "product",
    uniqueConstraints = @UniqueConstraint(columnNames = "sku")
)
public class Product {

    // Primary Key (auto-increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Product name (cannot be null)
    @NotNull(message = "Name cannot be null")
    private String name;

    // Product category (cannot be null)
    @NotNull(message = "Category cannot be null")
    private String category;

    // Product price (cannot be null)
    @NotNull(message = "Price cannot be null")
    private Double price;

    // SKU (unique & cannot be null)
    @NotNull(message = "SKU cannot be null")
    private String sku;

    // One-to-Many relationship with Inventory
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonManagedReference("inventory-product")
    private List<Inventory> inventories;

    // Default constructor
    public Product() {
    }

    // Parameterized constructor
    public Product(String name, String category, Double price, String sku) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.sku = sku;
    }

    // Getters and Setters

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}