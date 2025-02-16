package com.project.auth.controller;

import com.project.auth.model.RegisterRequest;
import com.project.auth.service.AuthenticationService;
import io.micronaut.data.connection.annotation.Connectable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Controller("/auth")
@Secured(SecurityRule.IS_ANONYMOUS)// Allow unauthenticated access to these endpoints
@Transactional
@Connectable
public class AuthenticationController {

    @Inject
    private AuthenticationService authenticationService;

    @Post("/register")
    public HttpResponse<?> register(@Body RegisterRequest request) {
        try {
            return authenticationService.register(request);
        } catch (Exception e) {
            return HttpResponse.badRequest("Registration failed: " + e.getMessage());
        }
    }
}