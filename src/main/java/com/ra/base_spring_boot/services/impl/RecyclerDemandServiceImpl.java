package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.resp.RecyclerDemandsResponse;
import com.ra.base_spring_boot.model.RecyclerDemands;
import com.ra.base_spring_boot.repository.IRecyclerDemandRepository;
import com.ra.base_spring_boot.services.ICloudinaryService;
import com.ra.base_spring_boot.services.IRecyclerDemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecyclerDemandServiceImpl implements IRecyclerDemandService {

    private final IRecyclerDemandRepository recyclerDemandsRepository;
    private final ICloudinaryService cloudinaryService;

    @Override
    public List<RecyclerDemandsResponse> findAll() {
        return recyclerDemandsRepository.findAll()
                .stream()
                .map(item -> new RecyclerDemandsResponse(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getImageUrl(),
                        item.getLatitude(),
                        item.getLongitude()
                ))
                .toList();
    }

    @Override
    public RecyclerDemands createRecyclerDemand(MultipartFile file, String name, String description, String street, String ward, String district, String city, String phone) {
        Map uploadResult = cloudinaryService.upload(file);

        RecyclerDemands demand = RecyclerDemands.builder()
                .name(name)
                .description(description)
                .public_id((String) uploadResult.get("public_id"))
                .imageUrl((String) uploadResult.get("secure_url"))
                .street(street)
                .ward(ward)
                .district(district)
                .city(city)
                .phone(phone)
                .status(true)
                .build();

        return recyclerDemandsRepository.save(demand);
    }
}
