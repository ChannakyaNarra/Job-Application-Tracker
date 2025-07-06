package com.clueper.funapptracker.dto;

public class AuthResponseDTO {
    private String message;
    private String token; // Will be used for login response

    public AuthResponseDTO(String message) {
        this.message = message;
    }

    public AuthResponseDTO(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthResponseDTO{" +
                "message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
