package com.project.auth.model;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@MappedEntity("users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String roles; // Store roles as a comma-separated string

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long id, String username, String password) {
        this.username = username;
        this.password = password;
        this.id=id;
    }

    // ... other fields and constructors

    public List<String> getRoles() {
        if (roles != null && !roles.isEmpty()) {
            return Arrays.asList(roles.split(","));
        }
        return List.of();
    }


}
