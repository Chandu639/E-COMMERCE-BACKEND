package com.ecommerce.dto.AuthDTO;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
