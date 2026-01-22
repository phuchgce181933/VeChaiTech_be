package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.model.WalletDepositTransaction;
import com.ra.base_spring_boot.model.WalletRecycler;
import com.ra.base_spring_boot.model.constants.DepositStatus;
import com.ra.base_spring_boot.repository.IDepositTransactionRepository;
import com.ra.base_spring_boot.repository.IWalletRecyclerRepository;
import com.ra.base_spring_boot.security.principle.MyUserDetails;
import com.ra.base_spring_boot.services.IPayOSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final IWalletRecyclerRepository walletRepo;
    private final IDepositTransactionRepository depositRepo;
    private final IPayOSService payOSService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(
            @RequestParam BigDecimal amount,
            @AuthenticationPrincipal MyUserDetails userDetails
    ) {
        User user = userDetails.getUser();

        WalletRecycler wallet = walletRepo.findByRecyclerId(user.getId())
                .orElseGet(() -> walletRepo.save(new WalletRecycler(user)));

        Long orderCode = System.currentTimeMillis() / 1000;

        depositRepo.save(
                WalletDepositTransaction.builder()
                        .wallet(wallet)
                        .amount(amount)
                        .orderCode(orderCode)
                        .status(DepositStatus.PENDING)
                        .description("Nạp tiền ví recycler")
                        .build()
        );

        String checkoutUrl = payOSService.createPaymentLink(
                orderCode,
                amount,
                "Nạp tiền ví recycler"
        );

        return ResponseEntity.ok(Map.of("checkoutUrl", checkoutUrl));
    }

}
