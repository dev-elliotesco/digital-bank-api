package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.dtos.TransactionRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.mappers.TransactionMapper;
import com.dev_elliotesco.digital_bank.models.Transaction;
import com.dev_elliotesco.digital_bank.repositories.AccountRepository;
import com.dev_elliotesco.digital_bank.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    public Mono<TransactionResponseDTO> makeTransaction(String accountId, TransactionRequestDTO request) {
        return accountRepository.findById(accountId)
                .flatMap(account -> {
                    Transaction transaction = transactionMapper.toTransaction(request);
                    return transactionRepository.save(transaction)
                            .map(savedTransaction -> transactionMapper.toTransactionResponseDTO(savedTransaction, accountId));
                });
    }

}
