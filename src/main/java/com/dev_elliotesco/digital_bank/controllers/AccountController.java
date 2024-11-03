package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.dtos.AccountRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.AccountResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionSummaryDTO;
import com.dev_elliotesco.digital_bank.mappers.AccountMapper;
import com.dev_elliotesco.digital_bank.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountResponseDTO> createAccount(@PathVariable String userId, @Valid @RequestBody AccountRequestDTO accountRequest) {
        return accountService.createAccount(userId, accountRequest);
    }

    @GetMapping("/balance/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Double> getBalance(@PathVariable String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @GetMapping("/withdrawals/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<TransactionResponseDTO>> getWithdrawals(@PathVariable String accountNumber) {
        return accountService.getWithdrawals(accountNumber);
    }

    @GetMapping("/deposits/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<TransactionResponseDTO>> getDeposits(@PathVariable String accountNumber) {
        return accountService.getDeposits(accountNumber);
    }

    @GetMapping("/transactions/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<TransactionSummaryDTO>> getTransactionSummary(@PathVariable String accountNumber) {
        return accountService.getTransactionSummary(accountNumber);
    }
}
