package com.dev_elliotesco.digital_bank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {

    private String id;
    private String accountId;
    private Double amount;
    private String type;
    private LocalDateTime timestamp;
}
