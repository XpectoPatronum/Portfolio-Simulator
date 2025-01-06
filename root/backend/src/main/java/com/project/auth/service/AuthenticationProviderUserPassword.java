package com.project.auth.service;

import io.micronaut.core.annotation.Blocking;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import jakarta.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;


@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider<HttpRequest<?>, String, String> {
    private final UserService userService;

    public AuthenticationProviderUserPassword(UserService userService) {
        this.userService = userService;
    }

    @Blocking
    @Override
    public AuthenticationResponse authenticate(@Nullable HttpRequest<?> context, AuthenticationRequest<String, String> authRequest) {
        String username = authRequest.getIdentity();
        String password = authRequest.getSecret();

        String storedPassword = userService.getPassword(username);
        if (storedPassword != null && BCrypt.checkpw(password, storedPassword)) {
            return AuthenticationResponse.success(username);
        }
        return AuthenticationResponse.failure();
    }
}


