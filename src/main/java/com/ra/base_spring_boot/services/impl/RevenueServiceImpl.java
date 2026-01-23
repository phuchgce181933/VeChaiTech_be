package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.DailyRevenueDTO;
import com.ra.base_spring_boot.model.constants.DepositStatus;
import com.ra.base_spring_boot.repository.IRevenueRepository;
import com.ra.base_spring_boot.repository.IWalletDepositTransactionRepository;
import com.ra.base_spring_boot.services.IRevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueServiceImpl implements IRevenueService {
    private final IWalletDepositTransactionRepository walletDepositRepository;

    @Override
    public BigDecimal getTodayRevenue() {
        LocalDate today = LocalDate.now();

        return walletDepositRepository.sumRevenueByTime(
                DepositStatus.SUCCESS,
                today.atStartOfDay(),
                today.atTime(23, 59, 59)
        );
    }

    @Override
    public BigDecimal getWeeklyRevenue() {
        LocalDate today = LocalDate.now();

        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return walletDepositRepository.sumRevenueByTime(
                DepositStatus.SUCCESS,
                startOfWeek.atStartOfDay(),
                endOfWeek.atTime(23, 59, 59)
        );
    }

    @Override
    public BigDecimal getMonthlyRevenue() {
        LocalDate today = LocalDate.now();

        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

        return walletDepositRepository.sumRevenueByTime(
                DepositStatus.SUCCESS,
                firstDay.atStartOfDay(),
                lastDay.atTime(23, 59, 59)
        );
    }

    @Override
    public BigDecimal getRevenueLastDays(int days) {
        LocalDate today = LocalDate.now();

        LocalDate startDate = today.minusDays(days - 1);
        // days = 1 → chỉ hôm nay
        // days = 6 → hôm nay + 5 ngày trước

        return walletDepositRepository.sumRevenueByTime(
                DepositStatus.SUCCESS,
                startDate.atStartOfDay(),
                today.atTime(23, 59, 59)
        );
    }

    @Override
    public List<DailyRevenueDTO> getRevenueLastDaysDetail(int days) {

        List<DailyRevenueDTO> result = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);

            BigDecimal revenue = walletDepositRepository.sumRevenueByTime(
                    DepositStatus.SUCCESS,
                    date.atStartOfDay(),
                    date.atTime(23, 59, 59)
            );

            result.add(new DailyRevenueDTO(
                    date,
                    revenue == null ? BigDecimal.ZERO : revenue
            ));
        }

        return result;
    }


}
