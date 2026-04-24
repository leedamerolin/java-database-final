package com.project.code.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.code.Repo.ReviewRepository;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Model.Review;
import com.project.code.Model.Customer;

import java.util.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // ================= GET ALL REVIEWS =================
    @GetMapping
    public Map<String, Object> getAllReviews() {

        Map<String, Object> response = new HashMap<>();

        List<Review> reviews = reviewRepository.findAll();

        response.put("reviews", reviews);

        return response;
    }

    // ================= GET REVIEWS BY STORE & PRODUCT =================
    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(@PathVariable Long storeId,
                                          @PathVariable Long productId) {

        Map<String, Object> response = new HashMap<>();

        List<Review> reviews = reviewRepository
                .findByStoreIdAndProductId(storeId, productId);

        List<Map<String, Object>> reviewList = new ArrayList<>();

        for (Review review : reviews) {

            Map<String, Object> reviewData = new HashMap<>();

            reviewData.put("comment", review.getComment());
            reviewData.put("rating", review.getRating());

            Customer customer = customerRepository.findByid(review.getCustomerId());

            if (customer != null) {
                reviewData.put("customerName", customer.getName());
            } else {
                reviewData.put("customerName", "Unknown");
            }

            reviewList.add(reviewData);
        }

        response.put("reviews", reviewList);

        return response;
    }
}