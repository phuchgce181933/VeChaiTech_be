package com.ra.base_spring_boot.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    private String fullName;
    private String username;
    private String email;
    private String phone;
    private String password; // password mới (nếu có)
    private Boolean status;
}
