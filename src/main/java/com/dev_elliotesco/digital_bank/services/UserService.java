package com.dev_elliotesco.digital_bank.services;

import com.dev_elliotesco.digital_bank.dtos.UserRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.UserResponseDTO;
import com.dev_elliotesco.digital_bank.mappers.UserMapper;
import com.dev_elliotesco.digital_bank.models.User;
import com.dev_elliotesco.digital_bank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Mono<UserResponseDTO> registerUser(UserRequestDTO userRequest) {
        User user = userMapper.toUser(userRequest);
        return userRepository.save(user)
                .map(userMapper::toUserResponseDTO);
    }
}
