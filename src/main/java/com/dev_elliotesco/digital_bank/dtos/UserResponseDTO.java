package com.dev_elliotesco.digital_bank.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    private String id;
    private String name;
    private String email;
    private String address;
    private List<AccountResponseDTO> accounts;
}
