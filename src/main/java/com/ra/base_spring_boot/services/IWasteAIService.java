package com.ra.base_spring_boot.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface IWasteAIService {
    String analyzeWasteImage(MultipartFile file) throws IOException;
}
