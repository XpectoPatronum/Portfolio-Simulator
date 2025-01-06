package com.project.auth.controller;

import com.project.auth.dto.UserDTO;
import com.project.auth.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Post("/register")
    public HttpResponse<?> register(@Body UserDTO userDTO) {
        logger.info(String.valueOf(userDTO));
        userService.register(userDTO);
        return HttpResponse.ok();
    }
}
