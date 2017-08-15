package com.cowerling.daytrace.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService {
    private static final String SECRET = "kdP1ERbm";
    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderService() {
        passwordEncoder = new StandardPasswordEncoder(SECRET);
    }

    public PasswordEncoder getEncoder() {
        return passwordEncoder;
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
