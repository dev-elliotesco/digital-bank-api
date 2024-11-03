package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.dtos.TransactionRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.handlers.InsufficientFundsException;
import com.dev_elliotesco.digital_bank.mappers.TransactionMapper;
import com.dev_elliotesco.digital_bank.models.Transaction;
import com.dev_elliotesco.digital_bank.repositories.AccountRepository;
import com.dev_elliotesco.digital_bank.repositories.TransactionRepository;
import com.dev_elliotesco.digital_bank.utils.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public Mono<TransactionResponseDTO> makeTransaction(String accountNumber, TransactionRequestDTO request) {
        return accountService.checkAccountExists(accountNumber)
                .then(accountRepository.findByNumber(accountNumber)
                        .flatMap(account -> {

                            return accountService.getBalance(accountNumber)
                                    .flatMap(totalBalance -> {
                                        if ("RETIRO".equals(request.getType()) && request.getAmount() > totalBalance) {
                                            return Mono.error(new InsufficientFundsException(ErrorMessages.INSUFFICIENT_FUNDS));
                                        }

                                        Transaction transaction = transactionMapper.toTransaction(request);

                                        return transactionRepository.save(transaction)
                                                .flatMap(savedTransaction -> {
                                                    account.getTransactions().add(savedTransaction);

                                                    return accountRepository.save(account)
                                                            .thenReturn(transactionMapper.toTransactionResponseDTO(savedTransaction, accountNumber));
                                                });
                                    });
                        })
                );
    }

}
