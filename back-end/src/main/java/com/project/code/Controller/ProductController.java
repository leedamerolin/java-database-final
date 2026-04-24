package com.project.code.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.code.Model.Product;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Service.ServiceClass;

import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ServiceClass serviceClass;

    @Autowired
    private InventoryRepository inventoryRepository;

    // ================= ADD PRODUCT =================
    @PostMapping
    public Map<String, String> addProduct(@RequestBody Product product) {

        Map<String, String> response = new HashMap<>();

        try {
            boolean isValid = serviceClass.validateProduct(product);

            if (!isValid) {
                response.put("message", "Product already exists");
            } else {
                productRepository.save(product);
                response.put("message", "Product added successfully");
            }

        } catch (Exception e) {
            response.put("message", "Error saving product");
        }

        return response;
    }

    // ================= GET PRODUCT BY ID =================
    @GetMapping("/{id}")
    public Map<String, Object> getProductbyId(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        Product product = productRepository.findById(id).orElse(null);

        response.put("products", product);

        return response;
    }

    // ================= UPDATE PRODUCT =================
    @PutMapping
    public Map<String, String> updateProduct(@RequestBody Product product) {

        Map<String, String> response = new HashMap<>();

        try {
            productRepository.save(product);
            response.put("message", "Product updated successfully");
        } catch (Exception e) {
            response.put("message", "Error updating product");
        }

        return response;
    }

    // ================= FILTER BY CATEGORY + NAME =================
    @GetMapping("/category/{name}/{category}")
    public Map<String, Object> filterbyCategoryProduct(@PathVariable String name,
                                                       @PathVariable String category) {

        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        if (name.equals("null")) {
            products = productRepository.findByCategory(category);
        } else if (category.equals("null")) {
            products = productRepository.findProductBySubName(name);
        } else {
            products = productRepository.findProductBySubNameAndCategory(name, category);
        }

        response.put("products", products);
        return response;
    }

    // ================= LIST ALL PRODUCTS =================
    @GetMapping
    public Map<String, Object> listProduct() {

        Map<String, Object> response = new HashMap<>();

        List<Product> products = productRepository.findAll();

        response.put("products", products);

        return response;
    }

    // ================= FILTER BY CATEGORY & STORE =================
    @GetMapping("filter/{category}/{storeid}")
    public Map<String, Object> getProductbyCategoryAndStoreId(@PathVariable String category,
                                                              @PathVariable Long storeid) {

        Map<String, Object> response = new HashMap<>();

        List<Product> products = productRepository.findProductByCategory(category, storeid);

        response.put("product", products);

        return response;
    }

    // ================= DELETE PRODUCT =================
    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable Long id) {

        Map<String, String> response = new HashMap<>();

        boolean exists = serviceClass.ValidateProductId(id);

        if (!exists) {
            response.put("message", "Product not present in database");
        } else {

            // Delete inventory first
            inventoryRepository.deleteByProductId(id);

            // Delete product
            productRepository.deleteById(id);

            response.put("message", "Product deleted successfully");
        }

        return response;
    }

    // ================= SEARCH PRODUCT =================
    @GetMapping("/searchProduct/{name}")
    public Map<String, Object> searchProduct(@PathVariable String name) {

        Map<String, Object> response = new HashMap<>();

        List<Product> products = productRepository.findProductBySubName(name);

        response.put("products", products);

        return response;
    }
}