package com.ra.base_spring_boot.services;

import org.springframework.web.multipart.MultipartFile;

import com.ra.base_spring_boot.model.OrderCancellation;

public interface IOrderCancellationService {

   OrderCancellation cancelOrder(
            Long orderId,
            Long userId,
            String reason,
            MultipartFile file
    );
}
