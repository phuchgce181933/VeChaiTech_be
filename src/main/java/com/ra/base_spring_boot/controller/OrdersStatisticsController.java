package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.services.IOrdersStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/orders/statistics")
@RequiredArgsConstructor
public class OrdersStatisticsController {
    private final IOrdersStatisticsService statisticsService;

    @GetMapping("/today")
    public ResponseEntity<?> today() {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(statisticsService.getTodayOrders())
                        .build()
        );
    }

    @GetMapping("/week")
    public ResponseEntity<?> week() {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(statisticsService.getThisWeekOrders())
                        .build()
        );
    }

    @GetMapping("/month")
    public ResponseEntity<?> month() {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(statisticsService.getThisMonthOrders())
                        .build()
        );
    }

    @GetMapping("/last-days")
    public ResponseEntity<?> lastDays(
            @RequestParam int days
    ) {
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(statisticsService.getLastDaysOrders(days))
                        .build()
        );
    }
}
