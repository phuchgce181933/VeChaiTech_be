package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.DailyRevenueDTO;
import com.ra.base_spring_boot.repository.IWalletDepositTransactionRepository;
import com.ra.base_spring_boot.services.IRevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/revenue")
@RequiredArgsConstructor
public class RevenueController {

    private final IRevenueService revenueService;

    @GetMapping("/today")
    public ResponseEntity<?> getTodayRevenue() {

        BigDecimal revenue = revenueService.getTodayRevenue();

        Map<String, Object> response = new HashMap<>();
        response.put("date", java.time.LocalDate.now());
        response.put("revenue", revenue);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/week")
    public ResponseEntity<?> getWeeklyRevenue() {
        return ResponseEntity.ok(
                Map.of(
                        "type", "WEEK",
                        "revenue", revenueService.getWeeklyRevenue()
                )
        );
    }
    @GetMapping("/month")
    public ResponseEntity<?> getMonthlyRevenue() {
        return ResponseEntity.ok(
                Map.of(
                        "type", "MONTH",
                        "revenue", revenueService.getMonthlyRevenue()
                )
        );
    }
    @GetMapping("/last-days")
    public ResponseEntity<?> getRevenueLastDays(
            @RequestParam int days
    ) {
        if (days <= 0) {
            return ResponseEntity.badRequest()
                    .body("days must be greater than 0");
        }

        return ResponseEntity.ok(
                Map.of(
                        "days", days,
                        "revenue", revenueService.getRevenueLastDays(days)
                )
        );
    }
    @GetMapping("/last-days-detail")
    public List<DailyRevenueDTO> getLastDaysDetail(
            @RequestParam int days
    ) {
        return revenueService.getRevenueLastDaysDetail(days);
    }
}
