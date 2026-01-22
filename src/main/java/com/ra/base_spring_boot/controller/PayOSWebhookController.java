package com.ra.base_spring_boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.base_spring_boot.dto.req.PayOSWebhookRequest;
import com.ra.base_spring_boot.services.IPayOSService;
import com.ra.base_spring_boot.services.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.model.webhooks.Webhook;


@RestController
@RequestMapping("/api/payos")
@RequiredArgsConstructor
public class PayOSWebhookController {
    private final IPayOSService payOSService;
    private final IWalletService walletService;
    private final PayOS payOS;
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody Webhook webhook) {
        try {
            var data = payOS.webhooks().verify(webhook);
            walletService.depositSuccess(data.getOrderCode());
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid webhook");
        }
    }
}
