package com.project.code.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.code.Model.*;
import com.project.code.Repo.*;
import com.project.code.Service.ServiceClass;
import com.project.code.Model.CombinedRequest;
import java.util.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;

    // ================= UPDATE INVENTORY =================
    @PutMapping
    public Map<String, String> updateInventory(@RequestBody CombinedRequest request) {

        Map<String, String> response = new HashMap<>();

        try {
            Product product = request.getProduct();
            Inventory inventory = request.getInventory();

            // Validate product ID
            boolean isValid = serviceClass.ValidateProductId(product.getId());

            if (!isValid) {
                response.put("message", "Product not found");
                return response;
            }

            Inventory existingInventory = serviceClass.getInventoryId(inventory);

            if (existingInventory != null) {
                productRepository.save(product);

                existingInventory.setStockLevel(inventory.getStockLevel());
                inventoryRepository.save(existingInventory);

                response.put("message", "Successfully updated product");
            } else {
                response.put("message", "No data available");
            }

        } catch (Exception e) {
            response.put("message", "Error updating inventory");
        }

        return response;
    }

    // ================= SAVE INVENTORY =================
    @PostMapping
    public Map<String, String> saveInventory(@RequestBody Inventory inventory) {

        Map<String, String> response = new HashMap<>();

        try {
            boolean isValid = serviceClass.validateInventory(inventory);

            if (!isValid) {
                response.put("message", "Data already present");
            } else {
                inventoryRepository.save(inventory);
                response.put("message", "Data saved successfully");
            }

        } catch (Exception e) {
            response.put("message", "Error saving inventory");
        }

        return response;
    }

    // ================= GET ALL PRODUCTS BY STORE =================
    @GetMapping("/{storeid}")
    public Map<String, Object> getAllProducts(@PathVariable Long storeid) {

        Map<String, Object> response = new HashMap<>();

        List<Product> products = productRepository.findProductsByStoreId(storeid);

        response.put("products", products);

        return response;
    }

    // ================= FILTER PRODUCTS =================
    @GetMapping("filter/{category}/{name}/{storeid}")
    public Map<String, Object> getProductName(@PathVariable String category,
                                              @PathVariable String name,
                                              @PathVariable Long storeid) {

        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        if (category.equals("null")) {
            products = productRepository.findByNameLike(storeid, name);
        } else if (name.equals("null")) {
            products = productRepository.findByCategoryAndStoreId(storeid, category);
        } else {
            products = productRepository.findByNameAndCategory(storeid, name, category);
        }

        response.put("product", products);
        return response;
    }

    // ================= SEARCH PRODUCT =================
    @GetMapping("search/{name}/{storeId}")
    public Map<String, Object> searchProduct(@PathVariable String name,
                                             @PathVariable Long storeId) {

        Map<String, Object> response = new HashMap<>();

        List<Product> products = productRepository.findByNameLike(storeId, name);

        response.put("product", products);

        return response;
    }

    // ================= DELETE PRODUCT =================
    @DeleteMapping("/{id}")
    public Map<String, String> removeProduct(@PathVariable Long id) {

        Map<String, String> response = new HashMap<>();

        boolean exists = serviceClass.ValidateProductId(id);

        if (!exists) {
            response.put("message", "Product not present in database");
        } else {
            inventoryRepository.deleteByProductId(id);
            response.put("message", "Product deleted successfully");
        }

        return response;
    }

    // ================= VALIDATE QUANTITY =================
    @GetMapping("validate/{quantity}/{storeId}/{productId}")
    public boolean validateQuantity(@PathVariable Integer quantity,
                                    @PathVariable Long storeId,
                                    @PathVariable Long productId) {

        Inventory inventory = inventoryRepository
                .findByProductIdandStoreId(productId, storeId);

        if (inventory == null) {
            return false;
        }

        return inventory.getStockLevel() >= quantity;
    }
}