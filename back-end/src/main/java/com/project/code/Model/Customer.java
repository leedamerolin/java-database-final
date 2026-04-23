package com.project.code.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
public class Customer {

    // Primary Key with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Customer name (cannot be null)
    @NotNull(message = "Name cannot be null")
    private String name;

    // Customer email (cannot be null)
    @NotNull(message = "Email cannot be null")
    private String email;

    // Customer phone (cannot be null)
    @NotNull(message = "Phone cannot be null")
    private String phone;

    // One customer can have multiple orders
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderDetails> orders;

    // Default constructor
    public Customer() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }
}