package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.services.IWasteAIService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/ai")
public class WasteAIController {
    private final IWasteAIService wasteAIService;

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Phân tích ảnh phế liệu",
            description = "Upload ảnh phế liệu để AI nhận dạng loại và ước tính giá trị"
    )
    public ResponseEntity<String> analyzeWasteImage(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("🟢 Nhận file: " + file.getOriginalFilename() + " (" + file.getSize() + " bytes)");
            String result = wasteAIService.analyzeWasteImage(file);
            System.out.println("✅ Kết quả AI trả về: " + result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("{ \"error\": \"" + e.getMessage() + "\" }");
        }
    }
}
