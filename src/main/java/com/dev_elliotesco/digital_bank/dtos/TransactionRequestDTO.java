package com.dev_elliotesco.digital_bank.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TransactionRequestDTO {
    @NotEmpty(message = "El tipo de transacción no puede estar vacío")
    @Pattern(regexp = "retiro|depósito", message = "El tipo de transacción debe ser 'retiro' o 'depósito'")
    private String type;

    @NotNull(message = "La cantidad no puede estar vacía")
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    private Double amount;
}
