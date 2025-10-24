package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.WasteListingsRequest;
import com.ra.base_spring_boot.dto.resp.WasteListingsResponse;
import com.ra.base_spring_boot.model.WasteListings;
import com.ra.base_spring_boot.model.constants.WasteType;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface IWasteListingsService {
    //trả về respone
    WasteListingsResponse createWasteListings(WasteListingsRequest request);
    // trả về nguyên mâũ
    WasteListings createlistWasteListings( String name, String description, BigDecimal price, WasteType wasteType, MultipartFile file);
    List<WasteListings> getAllWasteListings();
    WasteListings getWasteListingById(Long id);
    WasteListings updateWasteListings(Long id, String name, String description, BigDecimal price, WasteType wasteType, MultipartFile file);
    List<WasteListings> getWasteByRecyclerDemand(Long recyclerDemandId);
}
