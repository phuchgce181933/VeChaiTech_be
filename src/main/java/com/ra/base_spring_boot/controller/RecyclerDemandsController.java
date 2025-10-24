package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.resp.RecyclerDemandsResponse;
import com.ra.base_spring_boot.model.RecyclerDemands;
import com.ra.base_spring_boot.services.IRecyclerDemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/recycler-demands")
@RequiredArgsConstructor
public class RecyclerDemandsController {
    private final IRecyclerDemandService iRecyclerDemandService;

    @GetMapping
    public ResponseEntity<List<RecyclerDemandsResponse>> getAll() {
        return ResponseEntity.ok(iRecyclerDemandService.findAll());
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecyclerDemands> createRecyclerDemand(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("street") String street,
            @RequestParam("ward") String ward,
            @RequestParam("district") String district,
            @RequestParam("city") String city,
            @RequestParam("phone") String phone
    ) {
        RecyclerDemands demand = iRecyclerDemandService.createRecyclerDemand(
                file, name, description, street, ward, district, city, phone
        );
        return ResponseEntity.ok(demand);
    }
}
