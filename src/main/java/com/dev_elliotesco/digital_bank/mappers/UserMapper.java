package com.dev_elliotesco.digital_bank.mappers;

import com.dev_elliotesco.digital_bank.dtos.UserRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.UserResponseDTO;
import com.dev_elliotesco.digital_bank.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toUser(UserRequestDTO userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setAddress(userRequest.getAddress());
        return user;
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setAddress(user.getAddress());
        response.setAccounts(user.getAccounts() != null ? user.getAccounts().stream()
                .map(account -> new AccountMapper().toAccountResponseDTO(account))
                .collect(Collectors.toList()) : new ArrayList<>());
        return response;
    }
}
