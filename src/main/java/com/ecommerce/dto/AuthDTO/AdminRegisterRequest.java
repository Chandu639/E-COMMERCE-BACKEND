package com.ecommerce.dto.AuthDTO;

import lombok.Data;

@Data
public class AdminRegisterRequest {
    private String email;
    private String password;
}

