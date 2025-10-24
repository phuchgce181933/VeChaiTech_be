package com.ra.base_spring_boot.controller;


import com.ra.base_spring_boot.model.Banner;
import com.ra.base_spring_boot.repository.IBannerRepository;
import com.ra.base_spring_boot.services.impl.BannerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerServiceImpl bannerService;

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Banner> createBanner(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "targetUrl", required = false) String targetUrl,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "endAt", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endAt) {
        return ResponseEntity.ok(
                bannerService.createBanner(file, title, targetUrl, position, endAt)
        );
    }

    @GetMapping("list")
    public ResponseEntity<List<Banner>> getAllBanners() {
        return ResponseEntity.ok(bannerService.getAllBanners());
    }

    @GetMapping("/list_banner_id={id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable Long id) {
        return ResponseEntity.ok(bannerService.getBannerById(id));
    }
    @PutMapping(value ="/update_banner={id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Banner> updateBanner(@PathVariable Long id,
                                               @RequestParam(value = "file", required = false) MultipartFile file,
                                               @RequestParam(value = "title", required = false) String title,
                                               @RequestParam(value = "targetUrl", required = false) String targetUrl,
                                               @RequestParam(value = "position", required = false) String position,
                                               @RequestParam(value = "status", required = false) Boolean status) {
        return ResponseEntity.ok(bannerService.updateBanner(id, file, title, targetUrl, position, status));
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.noContent().build();
    }
}
