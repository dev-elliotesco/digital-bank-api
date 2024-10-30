package com.dev_elliotesco.digital_bank.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String name;

    @NotEmpty(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Formato de correo electrónico no válido")
    private String email;

    @NotEmpty(message = "La dirección no puede estar vacía")
    private String address;
}
