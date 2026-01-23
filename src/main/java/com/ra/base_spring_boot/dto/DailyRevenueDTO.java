package com.ra.base_spring_boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyRevenueDTO {
    private LocalDate date;
    private BigDecimal revenue;
}
