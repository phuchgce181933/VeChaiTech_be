package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.model.ContactRequest;
import com.ra.base_spring_boot.services.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
    @Autowired
    private IContactService contactService;

    @PostMapping
    public ResponseEntity<?> submit(@RequestBody ContactRequest request) {
        contactService.submitContact(request);
        return ResponseEntity.ok("Gửi yêu cầu thành công");
    }
}
