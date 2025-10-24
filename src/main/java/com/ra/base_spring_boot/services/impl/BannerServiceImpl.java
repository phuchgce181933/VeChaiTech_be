package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.model.Banner;

import com.ra.base_spring_boot.repository.IBannerRepository;
import com.ra.base_spring_boot.services.IBannerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements IBannerService {
    private final IBannerRepository iBannerRepository;
    private final CloudinaryServiceImpl cloudinaryService;

    @Override
    public Banner createBanner(MultipartFile file, String title, String targetUrl, String position, LocalDateTime  endAt) {
        Map uploadResult = cloudinaryService.upload(file);

        Banner banner = Banner.builder()
                .publicId((String) uploadResult.get("public_id"))
                .bannerUrl((String) uploadResult.get("secure_url"))
                .title(title)
                .targetUrl(targetUrl)
                .position(position)
                .status(true)
                .startAt(LocalDateTime.now())
                .endAt(endAt)
                .build();

        return iBannerRepository.save(banner);
    }

    @Override
    public List<Banner> getAllBanners() {
        return iBannerRepository.findAll();
    }

    @Override
    public Banner getBannerById(Long id) {
        return iBannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
    }

    @Override
    public Banner updateBanner(Long id, MultipartFile file, String title, String targetUrl, String position, Boolean status) {
        Banner banner = getBannerById(id);

        if (file != null && !file.isEmpty()) {
            // Xóa ảnh cũ trên Cloudinary
            cloudinaryService.delete(banner.getPublicId());

            // Upload ảnh mới
            Map uploadResult = cloudinaryService.upload(file);
            banner.setPublicId((String) uploadResult.get("public_id"));
            banner.setBannerUrl((String) uploadResult.get("secure_url"));
        }

        banner.setTitle(title);
        banner.setTargetUrl(targetUrl);
        banner.setPosition(position);
        banner.setStatus(status);

        return iBannerRepository.save(banner);

    }

    @Override
    public void deleteBanner(Long id) {
        // kiểm tra id có tồn tại trong DB không
        if (!iBannerRepository.existsById(id)) {
            throw new EntityNotFoundException("Banner với id " + id + " không tồn tại");
        }
        //lấy banner ra
        Banner banner = iBannerRepository.findById(id).orElse(null);
        //kiểm tra null (phòng trường hợp bất thường)
        if (banner == null) {
            throw new EntityNotFoundException("Không tìm thấy banner với id " + id);
        }
        //xóa trên Cloudinary và DB
        cloudinaryService.delete(banner.getPublicId());
        iBannerRepository.delete(banner);
        //xóa hẳn cloud
//        Banner banner = bannerRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Banner not found"));
//
//        try {
//            // Xóa ảnh trên Cloudinary
//            cloudinary.uploader().destroy(banner.getPublicId(), ObjectUtils.emptyMap());
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to delete image from Cloudinary", e);
//        }
//
//        // Xóa record trong DB
//        bannerRepository.delete(banner);
        // mẫu đơn giản hóa
//        Banner banner = getBannerById(id); // giả sử hàm này trả về null nếu không có
//
//        if (banner == null) {
//            throw new EntityNotFoundException("Banner với id " + id + " không tồn tại");
//        }
//
//        // Nếu tìm thấy thì xóa trên Cloudinary và DB
//        cloudinaryService.delete(banner.getPublicId());
//        iBannerRepository.delete(banner);
    }
}
