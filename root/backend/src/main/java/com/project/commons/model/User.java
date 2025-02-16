package com.project.commons.model;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.*;

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
    private String name;
    private String password;
    private String roles; // Store roles as a comma-separated string




    public User(long id, String name, String username, String password) {
        this.name=name;
        this.username = username;
        this.password = password;
        this.id=id;
    }

    public User(String name, String username, String encodedPassword) {
        this.name=name;
        this.username=username;
        this.password=encodedPassword;
    }

    // ... other fields and constructors

    public List<String> getRoles() {
        if (roles != null && !roles.isEmpty()) {
            return Arrays.asList(roles.split(","));
        }
        return List.of();
    }


}
