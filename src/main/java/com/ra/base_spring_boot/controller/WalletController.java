package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.model.WalletDepositTransaction;
import com.ra.base_spring_boot.model.WalletRecycler;
import com.ra.base_spring_boot.model.constants.DepositStatus;
import com.ra.base_spring_boot.repository.IDepositTransactionRepository;
import com.ra.base_spring_boot.repository.IWalletRecyclerRepository;
import com.ra.base_spring_boot.security.principle.MyUserDetails;
import com.ra.base_spring_boot.services.IPayOSService;
import com.ra.base_spring_boot.services.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final IWalletRecyclerRepository walletRepo;
    private final IDepositTransactionRepository depositRepo;
    private final IPayOSService payOSService;
    private final IWalletService walletService;
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(
            @RequestParam BigDecimal amount,
            @AuthenticationPrincipal MyUserDetails userDetails
    ) {
        if (amount.compareTo(BigDecimal.valueOf(10000)) < 0) {
            return ResponseEntity.badRequest().body("Minimum deposit is 10.000");
        }

        User user = userDetails.getUser();

        // lấy hoặc tạo ví
        WalletRecycler wallet = walletRepo.findByRecyclerId(user.getId())
                .orElseGet(() -> walletRepo.save(new WalletRecycler(user)));

        Long orderCode = System.currentTimeMillis();

        // lưu giao dịch nạp (PENDING)
        depositRepo.save(
                WalletDepositTransaction.builder()
                        .wallet(wallet)
                        .recyclerId(user.getId())
                        .amount(amount)
                        .orderCode(orderCode)
                        .status(DepositStatus.PENDING)
                        .description("Nạp tiền ví recycler")
                        .build()
        );

        // tạo link PayOS
        String checkoutUrl = payOSService.createPaymentLink(
                orderCode,
                amount,
                "Nạp tiền ví recycler"
        );

        return ResponseEntity.ok(Map.of(
                "checkoutUrl", checkoutUrl
        ));
    }
//    @GetMapping("/recycler/{id}/balance")
//    public ResponseEntity<?> getBalance(@PathVariable Long id) {
//        BigDecimal balance = walletService.getBalanceByRecyclerId(id);
//        return ResponseEntity.ok(Map.of("data", balance));
//    }
}
