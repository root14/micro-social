package com.root14.gateway.model;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
