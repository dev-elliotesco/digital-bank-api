package com.dev_elliotesco.digital_bank.dtos;

import lombok.Data;

@Data
public class TransactionSummaryDTO {
    private String id;
    private String type;
    private double amount;
}
