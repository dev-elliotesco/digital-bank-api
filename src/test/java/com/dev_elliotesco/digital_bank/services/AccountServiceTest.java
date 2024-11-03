package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.dtos.AccountRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.AccountResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionSummaryDTO;
import com.dev_elliotesco.digital_bank.handlers.AccountAlreadyExistsException;
import com.dev_elliotesco.digital_bank.mappers.AccountMapper;
import com.dev_elliotesco.digital_bank.mappers.TransactionMapper;
import com.dev_elliotesco.digital_bank.models.Account;
import com.dev_elliotesco.digital_bank.models.Transaction;
import com.dev_elliotesco.digital_bank.models.User;
import com.dev_elliotesco.digital_bank.repositories.AccountRepository;
import com.dev_elliotesco.digital_bank.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private UserRepository userRepository;
    private AccountMapper accountMapper;
    private TransactionMapper transactionMapper;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        userRepository = mock(UserRepository.class);
        accountMapper = mock(AccountMapper.class);
        transactionMapper = mock(TransactionMapper.class);
        accountService = new AccountService(accountRepository, userRepository, accountMapper, transactionMapper);
    }

    @Test
    void createAccount() {
        String userId = "1";
        AccountRequestDTO accountRequest = new AccountRequestDTO();

        User user = new User();
        user.setId(userId);
        user.setAccounts(new ArrayList<>());

        Account account = new Account();
        account.setNumber(accountRequest.getNumber());
        account.setType(accountRequest.getType());

        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setNumber(account.getNumber());
        accountResponseDTO.setType(account.getType());

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));
        when(accountRepository.findByNumber(accountRequest.getNumber())).thenReturn(Mono.empty());
        when(accountMapper.toAccount(accountRequest)).thenReturn(account);
        when(userRepository.save(user)).thenReturn(Mono.just(user));
        when(accountRepository.save(account)).thenReturn(Mono.just(account));
        when(accountMapper.toAccountResponseDTO(account)).thenReturn(accountResponseDTO);

        StepVerifier.create(accountService.createAccount(userId, accountRequest))
                .expectNext(accountResponseDTO)
                .verifyComplete();

        verify(userRepository).save(user);
        verify(accountRepository).save(account);
        verify(accountMapper).toAccountResponseDTO(account);
    }

    @Test
    void getBalance() {
        String accountNumber = "1";

        Transaction deposit = new Transaction();
        deposit.setType("DEPOSITO");
        deposit.setAmount(100.0);

        Transaction withdrawal = new Transaction();
        withdrawal.setType("RETIRO");
        withdrawal.setAmount(50.0);

        Account account = new Account();
        account.setNumber(accountNumber);
        account.setTransactions(Arrays.asList(deposit, withdrawal));

        when(accountRepository.findByNumber(accountNumber)).thenReturn(Mono.just(account));

        StepVerifier.create(accountService.getBalance(accountNumber))
                .expectNext(50.0)
                .verifyComplete();

        verify(accountRepository).findByNumber(accountNumber);
    }

    @Test
    void getWithdrawals() {
        String accountNumber = "1";

        Transaction withdrawal1 = new Transaction();
        withdrawal1.setType("RETIRO");
        withdrawal1.setAmount(100.0);

        Transaction withdrawal2 = new Transaction();
        withdrawal2.setType("RETIRO");
        withdrawal2.setAmount(50.0);

        TransactionResponseDTO transactionResponseDTO1 = new TransactionResponseDTO();
        TransactionResponseDTO transactionResponseDTO2 = new TransactionResponseDTO();

        Account account = new Account();
        account.setNumber(accountNumber);
        account.setTransactions(Arrays.asList(withdrawal1, withdrawal2));

        when(accountRepository.findByNumber(accountNumber)).thenReturn(Mono.just(account));
        when(transactionMapper.toTransactionResponseDTO(withdrawal1, accountNumber)).thenReturn(transactionResponseDTO1);
        when(transactionMapper.toTransactionResponseDTO(withdrawal2, accountNumber)).thenReturn(transactionResponseDTO2);

        StepVerifier.create(accountService.getWithdrawals(accountNumber))
                .expectNext(Arrays.asList(transactionMapper.toTransactionResponseDTO(withdrawal1, accountNumber),
                        transactionMapper.toTransactionResponseDTO(withdrawal2, accountNumber)))
                .verifyComplete();

        verify(accountRepository).findByNumber(accountNumber);
    }

    @Test
    void getDeposits() {
        String accountNumber = "1";

        Transaction deposit1 = new Transaction();
        deposit1.setType("DEPOSITO");
        deposit1.setAmount(150.0);

        Transaction deposit2 = new Transaction();
        deposit2.setType("DEPOSITO");
        deposit2.setAmount(75.0);

        Account account = new Account();
        account.setNumber(accountNumber);
        account.setTransactions(Arrays.asList(deposit1, deposit2));

        TransactionResponseDTO transactionResponseDTO1 = new TransactionResponseDTO();
        TransactionResponseDTO transactionResponseDTO2 = new TransactionResponseDTO();

        when(accountRepository.findByNumber(accountNumber)).thenReturn(Mono.just(account));
        when(transactionMapper.toTransactionResponseDTO(deposit1, accountNumber)).thenReturn(transactionResponseDTO1);
        when(transactionMapper.toTransactionResponseDTO(deposit2, accountNumber)).thenReturn(transactionResponseDTO2);

        StepVerifier.create(accountService.getDeposits(accountNumber))
                .expectNext(Arrays.asList(transactionMapper.toTransactionResponseDTO(deposit1, accountNumber),
                        transactionMapper.toTransactionResponseDTO(deposit2, accountNumber)))
                .verifyComplete();

        verify(accountRepository).findByNumber(accountNumber);
    }

    @Test
    void getTransactionSummary() {
        String accountNumber = "1";

        Transaction transaction1 = new Transaction("1", "DEPOSITO", 100.0);
        Transaction transaction2 = new Transaction("2", "RETIRO", 50.0);

        Account account = new Account();
        account.setNumber(accountNumber);
        account.setTransactions(Arrays.asList(transaction1, transaction2));

        when(accountRepository.findByNumber(accountNumber)).thenReturn(Mono.just(account));

        when(transactionMapper.toTransactionSummaryDTO(any()))
                .thenAnswer(invocation -> {
                    Transaction transaction = invocation.getArgument(0);
                    return new TransactionSummaryDTO(transaction.getId(), transaction.getType(), transaction.getAmount());
                });

        StepVerifier.create(accountService.getTransactionSummary(accountNumber))
                .expectNextMatches(summaryList -> {
                    assert summaryList.size() == 2;
                    assert summaryList.get(0).getType().equals("DEPOSITO");
                    assert summaryList.get(0).getAmount() == 100.0;
                    assert summaryList.get(1).getType().equals("RETIRO");
                    assert summaryList.get(1).getAmount() == 50.0;
                    return true;
                })
                .verifyComplete();

        verify(accountRepository).findByNumber(accountNumber);
        verify(transactionMapper, times(2)).toTransactionSummaryDTO(any());
    }


    @Test
    void validateAccountNumberWhenAccountExists() {
        String accountNumber = "1";

        when(accountRepository.findByNumber(accountNumber)).thenReturn(Mono.just(new Account()));

        StepVerifier.create(accountService.validateAccountNumber(accountNumber))
                .expectErrorMatches(throwable -> throwable instanceof AccountAlreadyExistsException &&
                        throwable.getMessage().contains(accountNumber))
                .verify();

        verify(accountRepository).findByNumber(accountNumber);
    }

    @Test
    void saveAccountWhenUserAccountsAreNull() {
        String userId = "1";
        AccountRequestDTO accountRequest = new AccountRequestDTO("1", "AHORROS");
        User user = new User();
        Account account = new Account();
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();

        when(accountMapper.toAccount(accountRequest)).thenReturn(account);
        when(userRepository.save(user)).thenReturn(Mono.just(user));
        when(accountRepository.save(account)).thenReturn(Mono.just(account));
        when(accountMapper.toAccountResponseDTO(account)).thenReturn(accountResponseDTO);

        user.setAccounts(null);

        StepVerifier.create(accountService.saveAccount(user, accountRequest))
                .expectNext(accountResponseDTO)
                .verifyComplete();

        verify(userRepository).save(user);
        verify(accountRepository).save(account);
        verify(accountMapper).toAccountResponseDTO(account);

    }
}