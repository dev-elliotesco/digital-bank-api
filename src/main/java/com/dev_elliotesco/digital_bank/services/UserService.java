package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.models.User;
import com.dev_elliotesco.digital_bank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> registerUser(User user) {
        return userRepository.save(user);
    }
}
