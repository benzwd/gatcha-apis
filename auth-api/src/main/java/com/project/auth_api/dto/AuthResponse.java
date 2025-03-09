package com.project.auth_api.dto;

public class AuthResponse {
    public String getToken() {
        return token;
    }

    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}
