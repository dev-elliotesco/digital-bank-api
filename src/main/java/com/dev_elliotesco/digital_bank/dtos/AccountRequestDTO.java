package com.dev_elliotesco.digital_bank.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountRequestDTO {
    @NotEmpty(message = "El número de cuenta no puede estar vacío")
    private String number;

    @NotEmpty(message = "El tipo de cuenta no puede estar vacío")
    @Pattern(regexp = "ahorros|corriente", message = "El tipo de cuenta debe ser 'ahorros' o 'corriente'")
    private String type;
}
