package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.dtos.AccountRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.AccountResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionSummaryDTO;
import com.dev_elliotesco.digital_bank.mappers.AccountMapper;
import com.dev_elliotesco.digital_bank.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;


class AccountControllerTest {

    private final WebTestClient webTestClient;
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountControllerTest() {
        accountService = mock(AccountService.class);
        accountMapper = mock(AccountMapper.class);
        webTestClient = WebTestClient.bindToController(new AccountController(accountService, accountMapper)).build();
    }

    @Test
    void createAccount() {
        String userId = "1";
        AccountRequestDTO accountRequest = new AccountRequestDTO("123456", "AHORROS");
        AccountResponseDTO accountResponse = new AccountResponseDTO("1", "123456", "AHORROS");

        when(accountService.createAccount(userId, accountRequest)).thenReturn(Mono.just(accountResponse));

        webTestClient.post()
                .uri("/api/v1/accounts/{userId}", userId)
                .body(BodyInserters.fromValue(accountRequest))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AccountResponseDTO.class)
                .isEqualTo(accountResponse);

        verify(accountService).createAccount(userId, accountRequest);
    }

    @Test
    void getBalance() {
        String accountNumber = "123456";
        double balance = 500.0;

        when(accountService.getBalance(accountNumber)).thenReturn(Mono.just(balance));

        webTestClient.get()
                .uri("/api/v1/accounts/balance/{accountNumber}", accountNumber)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Double.class)
                .isEqualTo(balance);

        verify(accountService).getBalance(accountNumber);
    }

    @Test
    void getWithdrawals() {
        String accountNumber = "123456";
        List<TransactionResponseDTO> withdrawals = List.of(new TransactionResponseDTO("1", accountNumber, 100.0, "RETIRO", LocalDateTime.now()));

        when(accountService.getWithdrawals(accountNumber)).thenReturn(Mono.just(withdrawals));

        webTestClient.get()
                .uri("/api/v1/accounts/withdrawals/{accountNumber}", accountNumber)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransactionResponseDTO.class)
                .isEqualTo(withdrawals);

        verify(accountService).getWithdrawals(accountNumber);
    }

    @Test
    void getDeposits() {
        String accountNumber = "123456";
        List<TransactionResponseDTO> deposits = List.of(new TransactionResponseDTO("2", accountNumber, 100.0, "DEPOSITO", LocalDateTime.now()));

        when(accountService.getDeposits(accountNumber)).thenReturn(Mono.just(deposits));

        webTestClient.get()
                .uri("/api/v1/accounts/deposits/{accountNumber}", accountNumber)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransactionResponseDTO.class)
                .isEqualTo(deposits);

        verify(accountService).getDeposits(accountNumber);
    }

    @Test
    void getTransactionSummary_shouldReturnListOfTransactionSummaryDTO() {
        String accountNumber = "123456";
        List<TransactionSummaryDTO> transactionSummary = List.of(new TransactionSummaryDTO("1", "DEPOSITO", 100.0));

        when(accountService.getTransactionSummary(accountNumber)).thenReturn(Mono.just(transactionSummary));

        webTestClient.get()
                .uri("/api/v1/accounts/transactions/{accountNumber}", accountNumber)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransactionSummaryDTO.class)
                .isEqualTo(transactionSummary);

        verify(accountService).getTransactionSummary(accountNumber);
    }
}