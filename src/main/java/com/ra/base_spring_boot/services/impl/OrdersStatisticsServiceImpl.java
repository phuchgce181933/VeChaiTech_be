package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.OrderStatisticDTO;
import com.ra.base_spring_boot.repository.IOrdersStatisticsRepository;
import com.ra.base_spring_boot.services.IOrdersStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersStatisticsServiceImpl  implements IOrdersStatisticsService {

    private final IOrdersStatisticsRepository repository;

    private List<OrderStatisticDTO> mapResult(List<Object[]> raw) {
        return raw.stream()
                .map(r -> new OrderStatisticDTO(
                        ((java.sql.Date) r[0]).toLocalDate(),
                        ((Number) r[1]).longValue()
                ))
                .toList();
    }

    @Override
    public Long getTodayOrders() {
        LocalDate today = LocalDate.now();

        return mapResult(
                repository.countOrdersByDate(
                        today.atStartOfDay(),
                        today.atTime(23, 59, 59)
                )
        ).stream().mapToLong(OrderStatisticDTO::getTotalOrders).sum();
    }

    @Override
    public Long getThisWeekOrders() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.with(DayOfWeek.MONDAY);
        LocalDate end = start.plusDays(6);

        return mapResult(
                repository.countOrdersByDate(
                        start.atStartOfDay(),
                        end.atTime(23, 59, 59)
                )
        ).stream().mapToLong(OrderStatisticDTO::getTotalOrders).sum();
    }

    @Override
    public Long getThisMonthOrders() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);
        LocalDate end = today.withDayOfMonth(today.lengthOfMonth());

        return mapResult(
                repository.countOrdersByDate(
                        start.atStartOfDay(),
                        end.atTime(23, 59, 59)
                )
        ).stream().mapToLong(OrderStatisticDTO::getTotalOrders).sum();
    }

    @Override
    public List<OrderStatisticDTO> getLastDaysOrders(int days) {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusDays(days - 1);

        return mapResult(
                repository.countOrdersByDate(
                        start.atStartOfDay(),
                        today.atTime(23, 59, 59)
                )
        );
    }
}
