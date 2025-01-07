package com.project.auth.service;

import com.project.auth.model.User;
import com.project.auth.repository.UserRepository;
import io.micronaut.data.connection.annotation.Connectable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.Optional;


@Singleton
@Transactional
@Connectable
public class AuthenticationProviderUserPassword implements AuthenticationProvider<HttpRequest<?>, String, String> { // Explicitly specify generic types

    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoder;

    public AuthenticationProviderUserPassword(UserRepository userRepository, PasswordEncoderService passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationResponse authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<String, String> authenticationRequest) {
        String identity = authenticationRequest.getIdentity();
        String secret = authenticationRequest.getSecret();

        Optional<User> optionalUser = userRepository.findByUsername(identity);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(secret, user.getPassword())) {
                return AuthenticationResponse.success(identity, user.getRoles());
            }
        }

        return AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
    }
}