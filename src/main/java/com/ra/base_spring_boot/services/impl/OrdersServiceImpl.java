package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.OrdersRequest;
import com.ra.base_spring_boot.dto.resp.OrdersResponse;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.model.Orders;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.model.WasteListings;
import com.ra.base_spring_boot.repository.IOrdersRepository;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.repository.IWasteListingRepository;
import com.ra.base_spring_boot.services.IOrdersService;
import com.ra.base_spring_boot.services.IWalletRecyclerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements IOrdersService {
    
    private final IWalletRecyclerService walletRecyclerService;
    
    // Định nghĩa giá để xem địa chỉ (có thể cấu hình)
    private static final BigDecimal ADDRESS_REVEAL_COST = new BigDecimal("1500");
    private final IOrdersRepository ordersRepository;
    private final IUserRepository userRepository;
    private final IWasteListingRepository wasteListingRepository;
    
    @Override
    public OrdersResponse createOrder(OrdersRequest request) {
        // Validate customer exists
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new HttpNotFound("Customer not found"));
        
        // Validate waste listing exists
        WasteListings wasteListing = wasteListingRepository.findById(request.getWasteListingId())
                .orElseThrow(() -> new HttpNotFound("Waste listing not found"));
        
        // Create new order
        Orders order = Orders.builder()
                .customer(customer)
                .wasteListing(wasteListing)
                .addressPublic(request.getAddressPublic())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .addressFull(request.getAddressFull())
                .status("PENDING")
                .build();
        
        Orders savedOrder = ordersRepository.save(order);
        
        return convertToResponse(savedOrder);
    }
    
    @Override
    public OrdersResponse getOrderById(Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Order not found"));
        return convertToResponse(order);
    }
    
    @Override
    public List<OrdersResponse> getAllOrders() {
        return ordersRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrdersResponse> getOrdersByCustomerId(Long customerId) {
        return ordersRepository.findByCustomerId(customerId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrdersResponse> getOrdersByRecyclerId(Long recyclerId) {
        return ordersRepository.findByRecyclerId(recyclerId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrdersResponse> getOrdersByStatus(String status) {
        return ordersRepository.findByStatus(status)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public OrdersResponse updateOrderStatus(Long orderId, String status) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new HttpNotFound("Order not found"));
        
        order.setStatus(status);
        Orders updatedOrder = ordersRepository.save(order);
        
        return convertToResponse(updatedOrder);
    }
    
    @Override
    public OrdersResponse assignRecyclerToOrder(Long orderId, Long recyclerId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new HttpNotFound("Order not found"));
        // Kiểm tra recycler có đủ tiền không
        if (!walletRecyclerService.hasEnoughBalance(recyclerId, ADDRESS_REVEAL_COST)) {
            throw new HttpBadRequest("nạp tiền để có thể nhận đơn. Required: " + ADDRESS_REVEAL_COST + " VND to view order details");
        }
        
        // Thanh toán để xem địa chỉ
        walletRecyclerService.payForOrder(recyclerId, orderId, ADDRESS_REVEAL_COST);
        
        
        User recycler = userRepository.findById(recyclerId)
                .orElseThrow(() -> new HttpNotFound("Recycler not found"));
        
        order.setRecycler(recycler);
        order.setStatus("CLAIMED");
        Orders updatedOrder = ordersRepository.save(order);
        
        return convertToResponse(updatedOrder);
    }
    
    @Override
    public void deleteOrder(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new HttpNotFound("Order not found"));
        ordersRepository.delete(order);
    }
    
    private OrdersResponse convertToResponse(Orders order) {
        return OrdersResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .customerName(order.getCustomer() != null ? order.getCustomer().getFullName() : null)
                .recyclerId(order.getRecycler() != null ? order.getRecycler().getId() : null)
                .recyclerName(order.getRecycler() != null ? order.getRecycler().getFullName() : null)
                .wasteListingId(order.getWasteListing() != null ? order.getWasteListing().getId() : null)
                .wasteListingName(order.getWasteListing() != null ? order.getWasteListing().getName() : null)
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .addressPublic(order.getAddressPublic())
                .latitude(order.getLatitude())
                .longitude(order.getLongitude())
                .addressFull(order.getAddressFull())
                .customerPhone(order.getCustomer() != null ? order.getCustomer().getPhone() : null)
                .status(order.getStatus())
                .build();
    }
}
