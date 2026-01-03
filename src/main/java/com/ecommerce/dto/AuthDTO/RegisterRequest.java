package com.ecommerce.dto.AuthDTO;


import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}
