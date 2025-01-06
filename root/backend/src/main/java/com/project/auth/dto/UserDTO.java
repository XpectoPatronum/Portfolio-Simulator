package com.project.auth.dto;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Introspected
@Data
public class UserDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
