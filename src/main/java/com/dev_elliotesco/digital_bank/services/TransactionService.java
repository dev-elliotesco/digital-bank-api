package com.dev_elliotesco.digital_bank.services;

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
    private final AccountRepository accountRepository;

    public Mono<Transaction> makeTransaction(String accountId, Transaction transaction) {
        return accountRepository.findById(accountId)
                .flatMap(account -> {
                    account.getTransactions().add(transaction);
                    return accountRepository.save(account).then(transactionRepository.save(transaction));
                });
    }

}
