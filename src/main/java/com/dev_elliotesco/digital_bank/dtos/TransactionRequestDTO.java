package com.dev_elliotesco.digital_bank.dtos;

import com.dev_elliotesco.digital_bank.utils.ErrorMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    @NotEmpty(message = ErrorMessages.TRANSACTION_TYPE_NOT_EMPTY)
    @Pattern(regexp = ErrorMessages.TRANSACTION_TYPE, message = ErrorMessages.TRANSACTION_TYPE_NOT_VALID)
    private String type;

    @NotNull(message = ErrorMessages.TRANSACTION_AMOUNT_NOT_EMPTY)
    @Min(value = 0, message = ErrorMessages.TRANSACTION_AMOUNT_NOT_VALID)
    private Double amount;
}
