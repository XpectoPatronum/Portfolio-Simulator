package com.project.auth.service;

import com.project.auth.dto.UserDTO;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class UserService {
    private final Map<String, String> userStore = new HashMap<>();

    public void register(@Valid UserDTO userDTO) {
        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        userStore.put(userDTO.getUsername(), hashedPassword);
    }

    public String getPassword(String username) {
        return userStore.get(username);
    }
}
