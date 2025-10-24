package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.model.Banner;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public interface IBannerService {
    Banner createBanner(MultipartFile file, String title, String targetUrl, String position, LocalDateTime endAt);
    List<Banner> getAllBanners();
    Banner getBannerById(Long id);
    Banner updateBanner(Long id, MultipartFile file, String title, String targetUrl, String position, Boolean status);
    void deleteBanner(Long id);
}
