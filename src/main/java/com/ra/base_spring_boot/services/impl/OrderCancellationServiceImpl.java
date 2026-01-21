package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.model.OrderCancellation;
import com.ra.base_spring_boot.model.Orders;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.repository.IOrdersRepository;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.repository.OrderCancellationRepository;

import com.ra.base_spring_boot.services.ICloudinaryService;
import com.ra.base_spring_boot.services.IOrderCancellationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class OrderCancellationServiceImpl implements IOrderCancellationService {

    private final IOrdersRepository orderRepository;
    private final OrderCancellationRepository orderCancellationRepository;
    private final IUserRepository userRepository;
    private final ICloudinaryService cloudinaryService;

     @Override
    @Transactional
    public OrderCancellation cancelOrder(
            Long orderId,
            Long userId,
            String reason,
            MultipartFile file
    ) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if ("COMPLETED".equals(order.getStatus())) {
            throw new RuntimeException("Completed order cannot be cancelled");
        }

        if (orderCancellationRepository.existsByOrder_Id(orderId)) {
            throw new RuntimeException("Order already cancelled");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 Upload Cloudinary
        var uploadResult = cloudinaryService.upload(file);
        String evidenceUrl = uploadResult.get("secure_url").toString();

        OrderCancellation cancellation = OrderCancellation.builder()
                .order(order)
                .cancelledBy(user)
                .reason(reason)
                .evidenceUrl(evidenceUrl)
                .build();

        orderCancellationRepository.save(cancellation);

        order.setStatus("CANCELLED");
        orderRepository.save(order);

        return cancellation;
    }
}
