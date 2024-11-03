package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.dtos.TransactionRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class TransactionControllerTest {

    private final WebTestClient webTestClient;
    private final TransactionService transactionService;

    public TransactionControllerTest() {
        transactionService = mock(TransactionService.class);
        webTestClient = WebTestClient.bindToController(new TransactionController(transactionService)).build();
    }

    @Test
    void makeTransaction_shouldReturnTransactionResponseDTO() {
        String accountNumber = "123456";
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO("DEPOSITO", 100.0);
        TransactionResponseDTO transactionResponse = new TransactionResponseDTO("1", accountNumber, 100.0, "DEPOSITO", LocalDateTime.now());

        when(transactionService.makeTransaction(accountNumber, transactionRequest)).thenReturn(Mono.just(transactionResponse));

        webTestClient.post()
                .uri("/api/v1/transactions/{accountNumber}", accountNumber)
                .body(BodyInserters.fromValue(transactionRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransactionResponseDTO.class)
                .isEqualTo(transactionResponse);

        verify(transactionService).makeTransaction(accountNumber, transactionRequest);
    }
}