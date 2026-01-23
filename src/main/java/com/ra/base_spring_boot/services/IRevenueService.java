package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.DailyRevenueDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRevenueService {
    BigDecimal getTodayRevenue();
    BigDecimal getWeeklyRevenue();
    BigDecimal getMonthlyRevenue();
    BigDecimal getRevenueLastDays(int days);
    List<DailyRevenueDTO> getRevenueLastDaysDetail(int days);
}
