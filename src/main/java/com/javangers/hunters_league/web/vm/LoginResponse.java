package com.javangers.hunters_league.web.vm;

public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() { return token; }
}
