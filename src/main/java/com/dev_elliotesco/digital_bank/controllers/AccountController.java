package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.models.Account;
import com.dev_elliotesco.digital_bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Account> createAccount(@PathVariable String userId, @RequestBody Account account) {
        return accountService.createAccount(userId, account);
    }
}
