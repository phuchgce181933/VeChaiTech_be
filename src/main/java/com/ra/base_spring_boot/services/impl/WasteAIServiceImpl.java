package com.ra.base_spring_boot.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.base_spring_boot.configuration.GeminiConfig;
import com.ra.base_spring_boot.model.WasteListings;
import com.ra.base_spring_boot.repository.IWasteListingRepository;
import com.ra.base_spring_boot.services.IWasteAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WasteAIServiceImpl implements IWasteAIService {
    private final IWasteListingRepository wasteListingRepository;
    private static final String API_KEY = "AIzaSyDDGhsWqlFUx3-Y-mgBV-odx7lit1--baQ";

    @Override
    public String analyzeWasteImage(MultipartFile file) throws IOException {
        // 1️⃣ Encode ảnh sang Base64
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

        // 2️⃣ Lấy dữ liệu rác từ DB
        List<WasteListings> wasteList = wasteListingRepository.findAll();

        // 3️⃣ Tạo prompt giới hạn cho AI
        String wasteContext = wasteList.stream()
                .map(w -> "- " + w.getName() + " (" + w.getDescription() + "): " + w.getPrice() + " VND/" + w.getWasteType())
                .collect(Collectors.joining("\n"));

        String prompt = """
        Dưới đây là danh sách loại rác hiện có trong hệ thống:
        %s
        Bạn là một nữ trợ lý ai
        Dựa trên hình ảnh người dùng tải lên, hãy xác định loại rác phù hợp nhất trong danh sách trên.
        Hướng dẫn cụ thể:
        - Nếu món đồ **còn có thể sửa chữa hoặc bán lại được**, hãy:
           + Nói rằng món đồ **vẫn có thể tái sử dụng hoặc bán lại**,\s
           + Đưa ra **lời khuyên ngắn gọn** khuyến khích bán hoặc sửa chữa để tiết kiệm tài nguyên.
        - Nếu món đồ **không thể sửa được hoặc không còn giá trị sử dụng**,\s
           + **Không được nhắc đến việc sửa chữa hay tái sử dụng**,
           + Chỉ cần nói rằng **món đồ này không còn giá trị**, **nên bán cho ve chai tái chế**.
        - Cuối cùng, **thêm câu kết** như sau:
          "Đây là nhận định sơ bộ của hệ thống VeChaiTech AI.\s
          Nếu bạn có nhu cầu tư vấn chi tiết hơn hoặc cần đội ngũ hỗ trợ tận nơi,\s
          hãy liên hệ đội ngũ của chúng tôi để được hỗ trợ chính xác nhất."
        Trả về JSON theo mẫu sau:
        {
          "name": "tên loại rác",
          "description": "mô tả loại rác",
          "price": "giá thu mua",
          "reason": "giải thích ngắn gọn vì sao chọn loại này"
        }
        """.formatted(wasteContext);

        // 4️⃣ URL hợp lệ
        String urlStr = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        // 5️⃣ Body JSON
        String requestBody = """
        {
          "contents": [
            {
              "parts": [
                { "text": "%s" },
                {
                  "inline_data": {
                    "mime_type": "image/jpeg",
                    "data": "%s"
                  }
                }
              ]
            }
          ]
        }
        """.formatted(prompt.replace("\"", "\\\""), base64Image);

        // 6️⃣ Gửi request
        try (OutputStream os = conn.getOutputStream()) {
            os.write(requestBody.getBytes(StandardCharsets.UTF_8));
        }

        // 7️⃣ Đọc phản hồi
        int code = conn.getResponseCode();
        InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
        String response = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        is.close();

        // 8️⃣ Nếu lỗi
        if (code != 200) {
            return "{ \"status\": \"error\", \"message\": \"Gemini API trả về lỗi\", \"detail\": " + response + " }";
        }

        /// 9️⃣ Phân tích JSON để lấy text trả về
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode textNode = root.at("/candidates/0/content/parts/0/text");

        if (textNode.isMissingNode() || textNode.asText().isBlank()) {
            return "{ \"status\": \"error\", \"message\": \"Không nhận diện được loại rác.\" }";
        }

        String resultText = textNode.asText();

// 🧹 Làm sạch chuỗi: bỏ ```json ... ``` và phần mô tả thừa
        String cleanJson = resultText;

// Loại bỏ markdown code block
        if (cleanJson.contains("```json")) {
            cleanJson = cleanJson.substring(cleanJson.indexOf("```json") + 7);
        }
        if (cleanJson.contains("```")) {
            cleanJson = cleanJson.substring(0, cleanJson.indexOf("```"));
        }

// Cắt bỏ đoạn mô tả phía sau JSON (nếu có)
        int extraInfoIndex = cleanJson.indexOf("Đây là nhận định");
        if (extraInfoIndex != -1) {
            cleanJson = cleanJson.substring(0, extraInfoIndex).trim();
        }

// Làm sạch khoảng trắng, xuống dòng
        cleanJson = cleanJson.trim();

// ✅ Kiểm tra JSON hợp lệ
        try {
            mapper.readTree(cleanJson); // parse thử để đảm bảo hợp lệ
        } catch (Exception e) {
            return "{ \"status\": \"error\", \"message\": \"Kết quả AI không phải JSON hợp lệ\", \"raw\": " + mapper.writeValueAsString(cleanJson) + " }";
        }

// ✅ Trả về JSON sạch
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("result", mapper.readTree(cleanJson));

        return mapper.writeValueAsString(result);

    }
}
