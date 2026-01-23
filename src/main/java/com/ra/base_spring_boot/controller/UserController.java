package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.req.UserUpdateRequest;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/v1")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getProfileById(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404)
                    .body("User không tồn tại với id = " + id);
        }
    }
    // =================== SỬA USER ===================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request
    ) {
        User user = userService.updateProfile(id, request);
        return ResponseEntity.ok(user);
    }

    // =================== XOÁ USER ===================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Xoá user thành công");
    }
    // =================== DANH SÁCH USER ===================
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
