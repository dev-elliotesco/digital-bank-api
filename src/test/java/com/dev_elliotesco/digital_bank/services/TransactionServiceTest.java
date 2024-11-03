package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.dtos.TransactionRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.handlers.InsufficientFundsException;
import com.dev_elliotesco.digital_bank.mappers.TransactionMapper;
import com.dev_elliotesco.digital_bank.models.Account;
import com.dev_elliotesco.digital_bank.models.Transaction;
import com.dev_elliotesco.digital_bank.repositories.AccountRepository;
import com.dev_elliotesco.digital_bank.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionRepository transactionRepository;
    private TransactionMapper transactionMapper;
    private AccountRepository accountRepository;
    private AccountService accountService;
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionMapper = mock(TransactionMapper.class);
        accountRepository = mock(AccountRepository.class);
        accountService = mock(AccountService.class);

        transactionService = new TransactionService(transactionRepository, transactionMapper, accountRepository, accountService);
    }

    @Test
    void makeTransaction() {
        String accountNumber = "1";
        TransactionRequestDTO request = new TransactionRequestDTO("DEPOSITO", 100.0);
        Account account = new Account();
        Transaction transaction = new Transaction();
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();

        when(accountService.checkAccountExists(accountNumber)).thenReturn(Mono.empty());
        when(accountRepository.findByNumber(accountNumber)).thenReturn(Mono.just(account));
        when(accountService.getBalance(accountNumber)).thenReturn(Mono.just(200.0));
        when(transactionMapper.toTransaction(request)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
        when(accountRepository.save(account)).thenReturn(Mono.just(account));
        when(transactionMapper.toTransactionResponseDTO(transaction, accountNumber)).thenReturn(transactionResponseDTO);

        StepVerifier.create(transactionService.makeTransaction(accountNumber, request))
                .expectNext(transactionResponseDTO)
                .verifyComplete();

        verify(transactionRepository).save(transaction);
        verify(accountRepository).save(account);
    }

    @Test
    void testMakeTransactionInsufficientFunds() {
        String accountNumber = "1";
        TransactionRequestDTO request = new TransactionRequestDTO("RETIRO", 300.0);

        when(accountService.checkAccountExists(accountNumber)).thenReturn(Mono.empty());
        when(accountRepository.findByNumber(accountNumber)).thenReturn(Mono.just(new Account()));
        when(accountService.getBalance(accountNumber)).thenReturn(Mono.just(200.0));

        StepVerifier.create(transactionService.makeTransaction(accountNumber, request))
                .expectError(InsufficientFundsException.class)
                .verify();

        verify(transactionRepository, never()).save(any());
        verify(accountRepository, never()).save(any());
    }

}