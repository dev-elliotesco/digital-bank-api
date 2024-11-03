package com.dev_elliotesco.digital_bank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSummaryDTO {
    private String id;
    private String type;
    private double amount;
}
