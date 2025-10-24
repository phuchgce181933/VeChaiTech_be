package com.ra.base_spring_boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.base_spring_boot.dto.req.WasteListingsRequest;
import com.ra.base_spring_boot.dto.resp.WasteListingsResponse;
import com.ra.base_spring_boot.model.WasteListings;
import com.ra.base_spring_boot.model.constants.WasteType;
import com.ra.base_spring_boot.services.impl.WasteListingsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/wastelistings")
@RequiredArgsConstructor
public class WasteListingController {
    private final WasteListingsServiceImpl wasteListingsService;

    //đổi WasteListings về WasteListingsResponse để trả cho khứa này về trạng thái respone, createlistWasteListings --> createWasteListings
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<WasteListings> createWasteListing(@RequestParam("file") MultipartFile file, @RequestBody WasteListingsRequest request) {
//        WasteListings response = wasteListingsService.createlistWasteListings(file,request);
//        return ResponseEntity.ok(response);
//    }
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WasteListings> createWasteListing(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) BigDecimal price,
            @RequestParam(value = "wasteType", required = false) WasteType wasteType,
            @RequestPart(value = "file") MultipartFile file){
        return ResponseEntity.ok(
                wasteListingsService.createlistWasteListings(name, description, price, wasteType, file)
    );
    }
    @PutMapping(value = "/update={id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WasteListings> updateWasteListing(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) BigDecimal price,
            @RequestParam(value = "wasteType", required = false) WasteType wasteType,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        return ResponseEntity.ok(
                wasteListingsService.updateWasteListings(id, name, description, price, wasteType, file)
        );
    }
    @GetMapping("/list")
    public ResponseEntity<List<WasteListings>> getWasteListings() {
        List<WasteListings> findListing = wasteListingsService.getAllWasteListings();
        return ResponseEntity.ok().body(findListing);
    }

    //  hiển thị chất thải theo doanh nghiệp
    @GetMapping("/recycler/{recyclerId}")
    public List<WasteListings> getByRecyclerDemand(@PathVariable Long recyclerId) {
        return wasteListingsService.getWasteByRecyclerDemand(recyclerId);
    }
}
