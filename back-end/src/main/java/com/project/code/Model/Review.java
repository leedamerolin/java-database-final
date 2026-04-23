package com.project.code.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

@Document(collection = "reviews")
public class Review {

    // MongoDB primary key (auto-generated)
    @Id
    private String id;

    // Customer who created the review
    @NotNull(message = "CustomerId cannot be null")
    private Long customerId;

    // Product being reviewed
    @NotNull(message = "ProductId cannot be null")
    private Long productId;

    // Store associated with the product
    @NotNull(message = "StoreId cannot be null")
    private Long storeId;

    // Rating (out of 5)
    @NotNull(message = "Rating cannot be null")
    private Integer rating;

    // Optional comment
    private String comment;

    // Default constructor
    public Review() {
    }

    // Parameterized constructor
    public Review(Long customerId, Long productId, Long storeId, Integer rating, String comment) {
        this.customerId = customerId;
        this.productId = productId;
        this.storeId = storeId;
        this.rating = rating;
        this.comment = comment;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}