package com.dev_elliotesco.digital_bank.dtos;

import com.dev_elliotesco.digital_bank.utils.ErrorMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotEmpty(message = ErrorMessages.USER_NAME_NOT_EMPTY)
    private String name;

    @NotEmpty(message = ErrorMessages.USER_EMAIL_NOT_EMPTY)
    @Email(message = ErrorMessages.USER_EMAIL_NOT_VALID)
    private String email;

    @NotEmpty(message = ErrorMessages.USER_ADRRESS_NOT_EMPTY)
    private String address;
}
