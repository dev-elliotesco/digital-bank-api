package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.dtos.UserRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.UserResponseDTO;
import com.dev_elliotesco.digital_bank.services.UserService;
import jakarta.validation.Valid;
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
    public Mono<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userRequest) {
        return userService.registerUser(userRequest);
    }
}
