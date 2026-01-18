package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.OrdersRequest;
import com.ra.base_spring_boot.dto.resp.OrdersResponse;
import com.ra.base_spring_boot.model.Orders;

import java.util.List;

public interface IOrdersService {
    
    OrdersResponse createOrder(OrdersRequest request);
    
    OrdersResponse getOrderById(Long id);
    
    List<OrdersResponse> getAllOrders();
    
    List<OrdersResponse> getOrdersByCustomerId(Long customerId);
    
    List<OrdersResponse> getOrdersByRecyclerId(Long recyclerId);
    
    List<OrdersResponse> getOrdersByStatus(String status);
    
    OrdersResponse updateOrderStatus(Long orderId, String status);
    
    OrdersResponse assignRecyclerToOrder(Long orderId, Long recyclerId);
    
    void deleteOrder(Long orderId);
}
