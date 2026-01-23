package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.OrderStatisticDTO;
import com.ra.base_spring_boot.dto.req.UserUpdateRequest;
import com.ra.base_spring_boot.model.User;

import java.util.List;

public interface IUserService {
    User getProfileById(Long id);

    // Cập nhật thông tin user (cho sửa tất cả, password thì hash)
    User updateProfile(Long id, UserUpdateRequest request);

    void deleteById(Long id);

    List<User> getAllUsers();

}
