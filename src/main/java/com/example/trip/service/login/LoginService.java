package com.example.trip.service.login;

public interface LoginService {
    String authenticateAndGenerateToken(String username, String password);
}
