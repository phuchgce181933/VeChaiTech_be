package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.model.Transactions;
import com.ra.base_spring_boot.services.ITransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
//@RequestMapping("/api/v1/transactions")
public class TransactionsController {

//    private final ITransactionsService transactionsService;
//
//    public TransactionsController(ITransactionsService transactionsService) {
//        this.transactionsService = transactionsService;
//    }
//    // Lấy tọa độ mới nhất
//    @GetMapping("/current-location")
//    public ResponseEntity<Transactions> getCurrentLocation() {
//        Transactions transaction = transactionsService.getLatestTransaction();
//        return ResponseEntity.ok(transaction);
//    }
//
//    // Lưu tọa độ từ client
//    @PostMapping("/current-location")
//    public Map<String, String> currentLocation(@RequestBody Map<String, Object> body) {
//        double lat = Double.parseDouble(body.get("latitude").toString());
//        double lng = Double.parseDouble(body.get("longitude").toString());
//
//        // Gọi SerpAPI reverse geocoding
//        RestTemplate restTemplate = new RestTemplate();
//        String apiKey = "ac7e477b5cf10839ea4d5334be2ecdd097baa3663ccbd787e59e9f609eb1ad71";
//        String url = "https://serpapi.com/search.json?engine=google_maps&type=reverse_geocode&lat="
//                + lat + "&lng=" + lng + "&api_key=" + apiKey;
//
//        Map result = restTemplate.getForObject(url, Map.class);
//        List<Map> results = (List<Map>) result.get("results");
//        String address = results != null && !results.isEmpty()
//                ? (String) results.get(0).get("formatted_address")
//                : "Không xác định được địa điểm";
//
//        return Map.of("address", address);
//    }
//
//
//    @PostMapping
//    public ResponseEntity<Transactions> createTransaction(@RequestBody Transactions transaction) {
//        return ResponseEntity.ok(transactionsService.saveTransaction(transaction));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Transactions> updateTransaction(@PathVariable Long id, @RequestBody Transactions transaction) {
//        return ResponseEntity.ok(transactionsService.updateTransaction(id, transaction));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
//        transactionsService.deleteTransaction(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Transactions> getTransaction(@PathVariable Long id) {
//        return transactionsService.getTransactionById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Transactions>> getAllTransactions() {
//        return ResponseEntity.ok(transactionsService.getAllTransactions());
//    }
}
