package com.ra.base_spring_boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class OrderStatisticDTO {
    private LocalDate date;
    private Long totalOrders;
}
