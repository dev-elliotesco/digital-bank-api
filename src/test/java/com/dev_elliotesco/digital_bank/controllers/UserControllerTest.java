package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.dtos.UserRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.UserResponseDTO;
import com.dev_elliotesco.digital_bank.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

class UserControllerTest {

    private final WebTestClient webTestClient;
    private final UserService userService;

    public UserControllerTest() {
        userService = mock(UserService.class);
        webTestClient = WebTestClient.bindToController(new UserController(userService)).build();
    }

    @Test
    void registerUser() {
        UserRequestDTO userRequest = new UserRequestDTO("Elliot", "elliot@example.com","Calle 1 # 12-23" );
        UserResponseDTO userResponse = new UserResponseDTO();

        when(userService.registerUser(userRequest)).thenReturn(Mono.just(userResponse));

        webTestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(userRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDTO.class)
                .isEqualTo(userResponse);

        verify(userService).registerUser(userRequest);
    }

    @Test
    void getTotalBalanceForUser() {
        String userId = "1";
        double totalBalance = 1000.0;

        when(userService.getTotalBalanceForUser(userId)).thenReturn(Mono.just(totalBalance));

        webTestClient.get()
                .uri("/api/v1/users/total-balance/{userId}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class)
                .isEqualTo(totalBalance);

        verify(userService).getTotalBalanceForUser(userId);
    }
}