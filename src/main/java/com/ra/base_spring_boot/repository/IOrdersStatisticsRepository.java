package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.OrderStatisticDTO;
import com.ra.base_spring_boot.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrdersStatisticsRepository extends JpaRepository<Orders, Long> {
    // ===== Thống kê theo ngày =====
    @Query(
            value = """
            SELECT 
                DATE(o.created_at) AS order_date,
                COUNT(o.id) AS total_orders
            FROM orders o
            WHERE o.created_at BETWEEN :start AND :end
            GROUP BY DATE(o.created_at)
            ORDER BY DATE(o.created_at)
        """,
            nativeQuery = true
    )
    List<Object[]> countOrdersByDate(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
