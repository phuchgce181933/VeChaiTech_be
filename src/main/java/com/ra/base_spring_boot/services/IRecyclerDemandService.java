package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.resp.RecyclerDemandsResponse;
import com.ra.base_spring_boot.model.RecyclerDemands;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRecyclerDemandService {
    List<RecyclerDemandsResponse> findAll();
    RecyclerDemands createRecyclerDemand(
            MultipartFile file,
            String name,
            String description,
            String street,
            String ward,
            String district,
            String city,
            String phone
    );
}
