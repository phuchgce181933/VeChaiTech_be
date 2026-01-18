package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.WalletDepositRequest;
import com.ra.base_spring_boot.dto.req.WalletWithdrawRequest;
import com.ra.base_spring_boot.services.IWalletRecyclerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletRecyclerController {
    
    private final IWalletRecyclerService walletService;
    
    /**
     * @param recyclerId Recycler ID
     * @apiNote Get wallet info by recycler ID
     */
    @GetMapping("/recycler/{recyclerId}")
    public ResponseEntity<?> getWallet(@PathVariable Long recyclerId) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(walletService.getWalletByRecyclerId(recyclerId))
                        .build()
        );
    }
    
    /**
     * @param recyclerId Recycler ID
     * @apiNote Create wallet for new recycler
     */
    @PostMapping("/recycler/{recyclerId}/create")
    public ResponseEntity<?> createWallet(@PathVariable Long recyclerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .data(walletService.createWallet(recyclerId))
                        .build()
        );
    }
    
    /**
     * @param request WalletDepositRequest
     * @apiNote Deposit money into wallet
     */
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@Valid @RequestBody WalletDepositRequest request) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(walletService.deposit(request))
                        .build()
        );
    }
    
    /**
     * @param request WalletWithdrawRequest
     * @apiNote Withdraw money from wallet
     */
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@Valid @RequestBody WalletWithdrawRequest request) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(walletService.withdraw(request))
                        .build()
        );
    }
    
    /**
     * @param recyclerId Recycler ID
     * @apiNote Get current balance
     */
    @GetMapping("/recycler/{recyclerId}/balance")
    public ResponseEntity<?> getBalance(@PathVariable Long recyclerId) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(walletService.getBalance(recyclerId))
                        .build()
        );
    }
    
    /**
     * @param recyclerId Recycler ID
     * @apiNote Get transaction history
     */
    @GetMapping("/recycler/{recyclerId}/transactions")
    public ResponseEntity<?> getTransactionHistory(@PathVariable Long recyclerId) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(walletService.getTransactionHistory(recyclerId))
                        .build()
        );
    }
    
    /**
     * @param recyclerId Recycler ID
     * @param type Transaction type (DEPOSIT, WITHDRAW, PAYMENT, REFUND)
     * @apiNote Get transactions by type
     */
    @GetMapping("/recycler/{recyclerId}/transactions/{type}")
    public ResponseEntity<?> getTransactionsByType(
            @PathVariable Long recyclerId,
            @PathVariable String type) {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(walletService.getTransactionsByType(recyclerId, type))
                        .build()
        );
    }
}
