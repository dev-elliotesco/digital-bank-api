package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.dtos.UserRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.UserResponseDTO;
import com.dev_elliotesco.digital_bank.mappers.UserMapper;
import com.dev_elliotesco.digital_bank.models.Account;
import com.dev_elliotesco.digital_bank.models.User;
import com.dev_elliotesco.digital_bank.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private AccountService accountService;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        accountService = mock(AccountService.class);

        userService = new UserService(userRepository, userMapper, accountService);


    }

    @Test
     void registerUser() {
        UserRequestDTO userRequest = new UserRequestDTO();
        User user = new User();
        UserResponseDTO userResponse = new UserResponseDTO();

        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(Mono.just(user));
        when(userMapper.toUserResponseDTO(user)).thenReturn(userResponse);

        Mono<UserResponseDTO> result = userService.registerUser(userRequest);

        StepVerifier.create(result)
                .expectNext(userResponse)
                .verifyComplete();

        verify(userRepository).save(user);
        verify(userMapper).toUser(userRequest);
        verify(userMapper).toUserResponseDTO(user);
    }

    @Test
    void getTotalBalanceForUser() {
        String userId = "1";
        User user = new User();
        Account account = new Account();

        user.setAccounts(List.of(account));

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));
        when(accountService.getBalance(account.getNumber())).thenReturn(Mono.just(100.0));

        Mono<Double> result = userService.getTotalBalanceForUser(userId);

        StepVerifier.create(result)
                .expectNext(100.0)
                .verifyComplete();

        verify(userRepository).findById(userId);
        verify(accountService).getBalance(account.getNumber());
    }
}