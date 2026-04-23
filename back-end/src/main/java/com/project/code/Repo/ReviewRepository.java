package com.project.code.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.project.code.Model.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    // Find reviews by storeId and productId
    List<Review> findByStoreIdAndProductId(Long storeId, Long productId);
}