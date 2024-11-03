package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.dtos.AccountRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.AccountResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionSummaryDTO;
import com.dev_elliotesco.digital_bank.handlers.AccountAlreadyExistsException;
import com.dev_elliotesco.digital_bank.handlers.AccountNotFoundException;
import com.dev_elliotesco.digital_bank.handlers.UserNotFoundException;
import com.dev_elliotesco.digital_bank.mappers.AccountMapper;
import com.dev_elliotesco.digital_bank.mappers.TransactionMapper;
import com.dev_elliotesco.digital_bank.models.Account;
import com.dev_elliotesco.digital_bank.models.User;
import com.dev_elliotesco.digital_bank.repositories.AccountRepository;
import com.dev_elliotesco.digital_bank.repositories.UserRepository;
import com.dev_elliotesco.digital_bank.utils.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    public Mono<AccountResponseDTO> createAccount(String userId, AccountRequestDTO accountRequest) {
        return validateUser(userId)
                .flatMap(user -> validateAccountNumber(accountRequest.getNumber())
                        .then(saveAccount(user, accountRequest))
                );
    }

    public Mono<Double> getBalance(String accountNumber) {
        return checkAccountExists(accountNumber)
                .flatMap(account -> {
                    double totalBalance = account.getTransactions().stream()
                            .mapToDouble(transaction ->
                                    "DEPOSITO".equals(transaction.getType()) ? transaction.getAmount() : -transaction.getAmount())
                            .sum();
                    return Mono.just(totalBalance);
                });
    }

    public Mono<List<TransactionResponseDTO>> getWithdrawals(String accountNumber) {
        return checkAccountExists(accountNumber)
                .flatMap(account -> {
                    List<TransactionResponseDTO> withdrawals = account.getTransactions().stream()
                            .filter(transaction -> "RETIRO".equals(transaction.getType()))
                            .map(transaction -> transactionMapper.toTransactionResponseDTO(transaction, accountNumber))
                            .collect(Collectors.toList());
                    return Mono.just(withdrawals);
                });
    }

    public Mono<List<TransactionResponseDTO>> getDeposits(String accountNumber) {
        return checkAccountExists(accountNumber)
                .flatMap(account -> {
                    List<TransactionResponseDTO> deposits = account.getTransactions().stream()
                            .filter(transaction -> "DEPOSITO".equals(transaction.getType()))
                            .map(transaction -> transactionMapper.toTransactionResponseDTO(transaction, accountNumber))
                            .collect(Collectors.toList());
                    return Mono.just(deposits);
                });
    }

    public Mono<List<TransactionSummaryDTO>> getTransactionSummary(String accountNumber) {
        return checkAccountExists(accountNumber)
                .flatMap(account -> {
                    List<TransactionSummaryDTO> transactionsSummary = account.getTransactions().stream()
                            .map(transactionMapper::toTransactionSummaryDTO)
                            .collect(Collectors.toList());
                    return Mono.just(transactionsSummary);
                });
    }

    private Mono<User> validateUser(String userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException(ErrorMessages.USER_NOT_FOUND + userId)));
    }

    private Mono<Void> validateAccountNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .flatMap(existingAccount ->
                        Mono.error(new AccountAlreadyExistsException(ErrorMessages.ACCOUNT_ALREADY_EXISTS + accountNumber))
                )
                .then();
    }

    public Mono<Account> checkAccountExists(String accountNumber) {
        return accountRepository.findByNumber(accountNumber)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND + accountNumber)));
    }

    private Mono<AccountResponseDTO> saveAccount(User user, AccountRequestDTO accountRequest) {
        Account account = accountMapper.toAccount(accountRequest);

        if (user.getAccounts() == null) {
            user.setAccounts(new ArrayList<>());
        }
        user.getAccounts().add(account);

        return userRepository.save(user)
                .then(accountRepository.save(account))
                .map(accountMapper::toAccountResponseDTO);
    }
}
