package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.dtos.AccountRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.AccountResponseDTO;
import com.dev_elliotesco.digital_bank.handlers.AccountAlreadyExistsException;
import com.dev_elliotesco.digital_bank.handlers.UserNotFoundException;
import com.dev_elliotesco.digital_bank.mappers.AccountMapper;
import com.dev_elliotesco.digital_bank.models.Account;
import com.dev_elliotesco.digital_bank.models.User;
import com.dev_elliotesco.digital_bank.repositories.AccountRepository;
import com.dev_elliotesco.digital_bank.repositories.UserRepository;
import com.dev_elliotesco.digital_bank.utils.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;

    public Mono<AccountResponseDTO> createAccount(String userId, AccountRequestDTO accountRequest) {
        return validateUser(userId)
                .flatMap(user -> validateAccountNumber(accountRequest.getNumber())
                        .then(saveAccount(user, accountRequest))
                );
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
