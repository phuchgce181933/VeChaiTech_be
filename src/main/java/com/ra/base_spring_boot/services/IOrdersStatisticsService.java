package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.OrderStatisticDTO;

import java.util.List;

public interface IOrdersStatisticsService {
    Long getTodayOrders();
    Long getThisWeekOrders();
    Long getThisMonthOrders();

    List<OrderStatisticDTO> getLastDaysOrders(int days);
}
