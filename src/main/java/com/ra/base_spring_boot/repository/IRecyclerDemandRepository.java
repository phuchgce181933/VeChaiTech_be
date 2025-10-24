package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.model.RecyclerDemands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecyclerDemandRepository extends JpaRepository<RecyclerDemands, Integer> {
}
