package com.dev_elliotesco.digital_bank.dtos;

import com.dev_elliotesco.digital_bank.utils.ErrorMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountRequestDTO {
    @NotEmpty(message = ErrorMessages.ACCOUNT_NUMBER_NOT_EMPTY)
    private String number;

    @NotEmpty(message = ErrorMessages.ACCOUNT_TYPE_NOT_EMPTY)
    @Pattern(regexp = ErrorMessages.ACCOUNT_TYPE, message = ErrorMessages.ACCOUNT_TYPE_NOT_VALID)
    private String type;
}
