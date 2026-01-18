package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.WalletRecycler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IWalletRecyclerRepository extends JpaRepository<WalletRecycler, Long> {
    
    Optional<WalletRecycler> findByRecyclerId(Long recyclerId);
    
    Optional<WalletRecycler> findByRecyclerIdAndIsActive(Long recyclerId, Boolean isActive);
}
