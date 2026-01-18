package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, Long> {
    
    List<Orders> findByCustomerId(Long customerId);
    
    List<Orders> findByRecyclerId(Long recyclerId);
    
    List<Orders> findByStatus(String status);
    
    Optional<Orders> findByIdAndCustomerId(Long orderId, Long customerId);
}
