package com.dev_elliotesco.digital_bank.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {

    private String id;
    private String accountId;
    private Double amount;
    private String type;
    private LocalDateTime timestamp;
}
