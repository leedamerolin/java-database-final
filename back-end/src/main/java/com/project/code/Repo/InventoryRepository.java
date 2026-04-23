package com.project.code.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;

import com.project.code.Model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Find inventory by productId and storeId
    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId AND i.store.id = :storeId")
    Inventory findByProductIdandStoreId(Long productId, Long storeId);

    // Find all inventory records for a specific store
    List<Inventory> findByStore_Id(Long storeId);

    // Delete inventory by productId
    @Modifying
    @Transactional
    @Query("DELETE FROM Inventory i WHERE i.product.id = :productId")
    void deleteByProductId(Long productId);
}