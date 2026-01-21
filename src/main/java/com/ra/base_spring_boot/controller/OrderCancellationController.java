package com.ra.base_spring_boot.controller;
import com.ra.base_spring_boot.services.IOrderCancellationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderCancellationController {

    private final IOrderCancellationService orderCancellationService;

    @PostMapping(
        value = "/{orderId}/cancel",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> cancelOrder(
            @PathVariable Long orderId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("reason") String reason
    ) {
        return ResponseEntity.ok(
                orderCancellationService.cancelOrder(orderId, userId, reason, file)
        );
    }
}
