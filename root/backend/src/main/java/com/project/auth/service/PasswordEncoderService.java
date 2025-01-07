package com.project.auth.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.inject.Singleton;

@Singleton
public class PasswordEncoderService {

    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(12, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.verifyer()
                .verify(rawPassword.toCharArray(), encodedPassword)
                .verified;
    }
}