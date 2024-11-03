package com.dev_elliotesco.digital_bank.controllers;

import com.dev_elliotesco.digital_bank.controllers.docs.TransactionControllerDOC;
import com.dev_elliotesco.digital_bank.dtos.TransactionRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController implements TransactionControllerDOC {

    private final TransactionService transactionService;

    @PostMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TransactionResponseDTO> makeTransaction(@PathVariable String accountNumber, @Valid @RequestBody TransactionRequestDTO transactionRequest) {
        return transactionService.makeTransaction(accountNumber, transactionRequest);
    }
}
