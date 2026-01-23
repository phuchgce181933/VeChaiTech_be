package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.UserUpdateRequest;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getProfileById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User không tồn tại với id = " + id)
                );
    }

    @Override
    public User updateProfile(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User không tồn tại với id = " + id)
                );

        // ===== UPDATE ALL FIELD =====
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(request.getStatus());

        // ===== UPDATE PASSWORD (HASH) =====
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new HttpBadRequest("User không tồn tại với id = " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

