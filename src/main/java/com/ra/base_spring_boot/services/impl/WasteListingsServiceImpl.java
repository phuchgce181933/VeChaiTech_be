package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.WasteListingsRequest;
import com.ra.base_spring_boot.dto.resp.WasteListingsResponse;
import com.ra.base_spring_boot.model.WasteListings;
import com.ra.base_spring_boot.model.constants.WasteType;
import com.ra.base_spring_boot.repository.IWasteListingRepository;
import com.ra.base_spring_boot.services.IWasteListingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WasteListingsServiceImpl implements IWasteListingsService {
    private final IWasteListingRepository iWasteListingRepository;
    private final CloudinaryServiceImpl cloudinaryService;

    // respone
    @Override
    public WasteListingsResponse  createWasteListings(WasteListingsRequest request) {
        WasteListings wasteListings = WasteListings.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .wasteType(request.getWasteType())
                .status(true)
                .build();
        WasteListings savedListing = iWasteListingRepository.save(wasteListings);
        return WasteListingsResponse.builder()
                .name(savedListing.getName())
                .description(savedListing.getDescription())
                .price(savedListing.getPrice())
                .wasteType(savedListing.getWasteType())
                .build();
    }

    @Override
    public List<WasteListings> getAllWasteListings() {
        return iWasteListingRepository.findAll();

    }

    @Override
    public WasteListings getWasteListingById(Long id) {
        return iWasteListingRepository.findById(id).orElseThrow(() -> new RuntimeException("Waste not found"));
    }

    @Override
    public WasteListings updateWasteListings(Long id, String name, String description, BigDecimal price, WasteType wasteType, MultipartFile file) {
        WasteListings wasteListings = getWasteListingById(id);
        // Xử lý file mới nếu có
        if (file != null && !file.isEmpty()) {
            // Xóa ảnh cũ trên Cloudinary nếu có
            if (wasteListings.getWasteUrl() != null) {
                cloudinaryService.delete(wasteListings.getWasteUrl());
            }

            // Upload ảnh mới
            Map uploadResult = cloudinaryService.upload(file);
            wasteListings.setPublicId((String) uploadResult.get("public_id"));
            wasteListings.setWasteUrl((String) uploadResult.get("secure_url"));
        }

        // Chỉ cập nhật các trường nếu không null
        if (name != null) wasteListings.setName(name);
        if (description != null) wasteListings.setDescription(description);
        if (price != null) wasteListings.setPrice(price);
        if (wasteType != null) wasteListings.setWasteType(wasteType);

        return iWasteListingRepository.save(wasteListings);
    }

    @Override
    public List<WasteListings> getWasteByRecyclerDemand(Long recyclerDemandId) {
        return iWasteListingRepository.findByRecyclerDemandId(recyclerDemandId);
    }

    // nguyên mẫu
    @Override
    public WasteListings createlistWasteListings(String name, String description, BigDecimal price, WasteType wasteType, MultipartFile file) {
        //public WasteListings createlistWasteListings(MultipartFile file, WasteListingsRequest request) {
        Map uploadResult = cloudinaryService.upload(file);

        WasteListings wasteListings = WasteListings.builder()
                .name(name)
                .description(description)
                .price(price)
                .wasteType(wasteType)
                .wasteUrl((String) uploadResult.get("secure_url"))
                .publicId((String) uploadResult.get("public_id"))
                .build();
        return iWasteListingRepository.save(wasteListings);
    }
    @Override
    public void deleteWasteListing(Long id) {
        WasteListings waste = iWasteListingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Waste listing not found with id: " + id));

        // Nếu có ảnh Cloudinary / Firebase → xóa ảnh ở đây (optional)

        iWasteListingRepository.delete(waste);
    }

}
