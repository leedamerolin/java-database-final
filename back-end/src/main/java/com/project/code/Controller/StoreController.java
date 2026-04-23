package com.project.code.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import com.project.code.Model.PlaceOrderRequestDTO;
import java.util.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    // ================= ADD STORE =================
    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store) {

        Map<String, String> response = new HashMap<>();

        Store savedStore = storeRepository.save(store);

        response.put("message", "Store created successfully with id " + savedStore.getId());

        return response;
    }

    // ================= VALIDATE STORE =================
    @GetMapping("validate/{storeId}")
    public boolean validateStore(@PathVariable Long storeId) {

        Store store = storeRepository.findByid(storeId);

        if (store != null) {
            return true;
        }

        return false;
    }

    // ================= PLACE ORDER =================
    @PostMapping("/placeOrder")
    public Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO request) {

        Map<String, String> response = new HashMap<>();

        try {
            orderService.saveOrder(request);
            response.put("message", "Order placed successfully");
        } catch (Exception e) {
            response.put("Error", e.getMessage());
        }

        return response;
    }
}