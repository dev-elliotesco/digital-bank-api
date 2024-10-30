package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.dtos.AccountRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.AccountResponseDTO;
import com.dev_elliotesco.digital_bank.mappers.AccountMapper;
import com.dev_elliotesco.digital_bank.models.Account;
import com.dev_elliotesco.digital_bank.repositories.AccountRepository;
import com.dev_elliotesco.digital_bank.repositories.UserRepository;
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
        Account account = accountMapper.toAccount(accountRequest);

        return userRepository.findById(userId)
                .flatMap(user -> {
                    if (user.getAccounts() == null) {
                        user.setAccounts(new ArrayList<>());
                    }
                    user.getAccounts().add(account);

                    return userRepository.save(user)
                            .then(accountRepository.save(account))
                            .map(accountMapper::toAccountResponseDTO);
                });
    }
}
