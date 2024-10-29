package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.models.Account;
import com.dev_elliotesco.digital_bank.repositories.AccountRepository;
import com.dev_elliotesco.digital_bank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public Mono<Account> createAccount(String userId, Account account) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.getAccounts().add(account);
                    return userRepository.save(user).then(accountRepository.save(account));
                });
    }
}
