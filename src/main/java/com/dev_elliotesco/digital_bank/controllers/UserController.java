package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.models.User;
import com.dev_elliotesco.digital_bank.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
}
