package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.models.Transaction;
import com.dev_elliotesco.digital_bank.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transaction> makeTransaction(@PathVariable String accountId, @RequestBody Transaction transaction) {
        return transactionService.makeTransaction(accountId, transaction);
    }
}
