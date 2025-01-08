package com.project.auth.service;

import com.project.commons.model.User;
import com.project.commons.repository.UserRepository;
import com.project.auth.model.RegisterRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
public class AuthenticationService {
    @Inject
    private PasswordEncoderService passwordEncoder;

    @Inject
    private UserRepository userRepository;

    @Inject
    private JwtTokenGenerator jwtTokenGenerator;

    public HttpResponse<?> register(RegisterRequest request) {
        try {
            // Validate request
            if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
                return HttpResponse.badRequest("Username cannot be empty");
            }
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return HttpResponse.badRequest("Password cannot be empty");
            }

            // Check if username already exists
            if (userRepository.existsByUsername(request.getUsername())) {
                return HttpResponse.badRequest("Username already exists");
            }

            // Create new user
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            System.out.println(encodedPassword);
            User user = new User(request.getUsername(), encodedPassword);
            userRepository.save(user);

            // Return success response
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", user.getUsername());

            return HttpResponse.ok(response);

        } catch (Exception e) {
            return HttpResponse.serverError("Registration failed: " + e.getMessage());
        }
    }
}