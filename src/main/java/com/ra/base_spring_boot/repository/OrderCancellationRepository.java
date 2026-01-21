package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.OrderCancellation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancellationRepository extends JpaRepository<OrderCancellation, Long> {
    boolean existsByOrder_Id(Long orderId);
}
