package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.dtos.AccountRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.AccountResponseDTO;
import com.dev_elliotesco.digital_bank.mappers.AccountMapper;
import com.dev_elliotesco.digital_bank.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
}
