package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.OrdersRequest;
import com.ra.base_spring_boot.services.IOrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    
    private final IOrdersService ordersService;
    
    /**
     * @param ordersRequest OrdersRequest
     * @apiNote Create a new order
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrdersRequest ordersRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .data(ordersService.createOrder(ordersRequest))
                        .build()
        );
    }
    
    /**
     * @param id Order ID
     * @apiNote Get order by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(ordersService.getOrderById(id))
                        .build()
        );
    }
    
    /**
     * @apiNote Get all orders
     */
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(ordersService.getAllOrders())
                        .build()
        );
    }
    
    /**
     * @param customerId Customer ID
     * @apiNote Get orders by customer ID
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(ordersService.getOrdersByCustomerId(customerId))
                        .build()
        );
    }
    
    /**
     * @param recyclerId Recycler ID
     * @apiNote Get orders by recycler ID
     */
    @GetMapping("/recycler/{recyclerId}")
    public ResponseEntity<?> getOrdersByRecyclerId(@PathVariable Long recyclerId) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(ordersService.getOrdersByRecyclerId(recyclerId))
                        .build()
        );
    }
    
    /**
     * @param status Order status
     * @apiNote Get orders by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(ordersService.getOrdersByStatus(status))
                        .build()
        );
    }
    
    /**
     * @param orderId Order ID
     * @param status New status
     * @apiNote Update order status
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(ordersService.updateOrderStatus(orderId, status))
                        .build()
        );
    }
    
    /**
     * @param orderId Order ID
     * @param recyclerId Recycler ID
     * @apiNote Assign recycler to order
     */
    @PatchMapping("/{orderId}/assign-recycler")
    public ResponseEntity<?> assignRecyclerToOrder(
            @PathVariable Long orderId,
            @RequestParam Long recyclerId) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(ordersService.assignRecyclerToOrder(orderId, recyclerId))
                        .build()
        );
    }
    
    /**
     * @param id Order ID
     * @apiNote Delete order
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data("Order deleted successfully")
                        .build()
        );
    }
}
