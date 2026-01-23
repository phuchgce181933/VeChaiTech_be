package com.ra.base_spring_boot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.base_spring_boot.configuration.PayOSConfig;
import com.ra.base_spring_boot.configuration.PayOSSignatureUtil;
import com.ra.base_spring_boot.services.IWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.model.webhooks.Webhook;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/payos")
@RequiredArgsConstructor
@Slf4j
public class PayOSWebhookController {
    private final IWalletService walletService;
    private final PayOS payOS;
    private final ObjectMapper objectMapper;
    private final PayOSConfig payOSConfig;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String rawBody) {
        try {
            JsonNode root = objectMapper.readTree(rawBody);

            if (!root.has("signature")) {
                return ResponseEntity.ok("OK");
            }

            JsonNode data = root.get("data");
            String signature = root.get("signature").asText();

            if (!PayOSSignatureUtil.verify(
                    data,
                    signature,
                    payOSConfig.getChecksumKey()
            )) {
                return ResponseEntity.status(401).body("Invalid signature");
            }


            if ("00".equals(root.get("code").asText())) {
                walletService.depositSuccess(
                        data.get("orderCode").asLong()
                );
            }

            return ResponseEntity.ok("OK");

        } catch (RuntimeException e) {
            // nghiệp vụ chưa sẵn sàng → retry
            log.warn("Webhook retry needed: {}", e.getMessage());
            return ResponseEntity.status(400).body("Retry later");

        } catch (Exception e) {
            // lỗi thật
            log.error("Webhook error", e);
            return ResponseEntity.status(500).body("Server error");
        }
    }


    @GetMapping("/webhook")
    public ResponseEntity<String> webhookGet() {
        return ResponseEntity.ok(
                "Webhook endpoint is alive. POST only."
        );
    }

//    @PostMapping("/webhook")
//    public ResponseEntity<String> handleWebhook(
//            @RequestBody(required = false) String rawBody
//    ) {
//        try {
//            // PayOS verify webhook (body rỗng)
//            if (rawBody == null || rawBody.isBlank()) {
//                return ResponseEntity.ok("OK");
//            }
//
//            JsonNode root = objectMapper.readTree(rawBody);
//
//            if (!root.has("signature")) {
//                return ResponseEntity.ok("OK");
//            }
//
//            JsonNode data = root.get("data");
//            String signature = root.get("signature").asText();
//
//            boolean valid = PayOSSignatureUtil.verify(
//                    data,
//                    signature,
//                    payOSConfig.getChecksumKey()
//            );
//
//            if (!valid) {
//                return ResponseEntity.status(401).body("Invalid signature");
//            }
//
//            if ("00".equals(root.get("code").asText())) {
//                walletService.depositSuccess(
//                        data.get("orderCode").asLong()
//                );
//            }
//
//            return ResponseEntity.ok("OK");
//
//        } catch (Exception e) {
//            log.error("Webhook error", e);
//            return ResponseEntity.ok("OK"); // ⚠ KHÔNG trả 500 cho PayOS
//        }
//    }

}
