package com.project.code.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.code.Repo.*;
import com.project.code.Model.*;
import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.PurchaseProductDTO;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // ================= SAVE ORDER =================
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {

        // -------- Retrieve or Create Customer --------
        Customer customer = customerRepository.findByEmail(placeOrderRequest.getEmail());

        if (customer == null) {
            customer = new Customer();
            customer.setName(placeOrderRequest.getName());
            customer.setEmail(placeOrderRequest.getEmail());
            customer.setPhone(placeOrderRequest.getPhone());
            customer = customerRepository.save(customer);
        }

        // -------- Retrieve Store --------
        Store store = storeRepository.findByid(placeOrderRequest.getStoreId());

        if (store == null) {
            throw new RuntimeException("Store not found");
        }

        // -------- Create OrderDetails --------
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCustomer(customer);
        orderDetails.setStore(store);
        orderDetails.setTotalPrice(placeOrderRequest.getTotalPrice());
        orderDetails.setDate(LocalDateTime.now());

        orderDetails = orderDetailsRepository.save(orderDetails);

        // -------- Process Each Product --------
        List<PurchaseProductDTO> products = placeOrderRequest.getPurchaseProduct();

        for (PurchaseProductDTO item : products) {

            // Get inventory for product and store
            Inventory inventory = inventoryRepository
                    .findByProductIdandStoreId(item.getProductId(), placeOrderRequest.getStoreId());

            if (inventory == null) {
                throw new RuntimeException("Inventory not found for product");
            }

            // Update stock level
            inventory.setStockLevel(inventory.getStockLevel() - item.getQuantity());
            inventoryRepository.save(inventory);

            // Get product
            Product product = productRepository.findById(item.getProductId()).orElse(null);

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orderDetails);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());

            orderItemRepository.save(orderItem);
        }
    }
}