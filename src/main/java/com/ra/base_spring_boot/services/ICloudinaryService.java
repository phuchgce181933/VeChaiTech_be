package com.ra.base_spring_boot.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ICloudinaryService {
    Map upload(MultipartFile file);
    Map delete(String publicId);
}
