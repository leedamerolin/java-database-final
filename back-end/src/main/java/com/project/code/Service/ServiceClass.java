package com.project.code.Service;

import org.springframework.stereotype.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;

@Service
public class ServiceClass {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    // Constructor Injection
    public ServiceClass(InventoryRepository inventoryRepository,
                        ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    // ================= VALIDATE INVENTORY =================
    public boolean validateInventory(Inventory inventory) {

        Inventory existingInventory = inventoryRepository
                .findByProductIdandStoreId(
                        inventory.getProduct().getId(),
                        inventory.getStore().getId()
                );

        if (existingInventory != null) {
            return false;
        }

        return true;
    }

    // ================= VALIDATE PRODUCT =================
    public boolean validateProduct(Product product) {

        Product existingProduct = productRepository.findByName(product.getName());

        if (existingProduct != null) {
            return false;
        }

        return true;
    }

    // ================= VALIDATE PRODUCT ID =================
    public boolean ValidateProductId(long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return false;
        }

        return true;
    }

    // ================= GET INVENTORY =================
    public Inventory getInventoryId(Inventory inventory) {

        return inventoryRepository.findByProductIdandStoreId(
                inventory.getProduct().getId(),
                inventory.getStore().getId()
        );
    }
}