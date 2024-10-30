package com.dev_elliotesco.digital_bank.mappers;

import com.dev_elliotesco.digital_bank.dtos.TransactionRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.TransactionResponseDTO;
import com.dev_elliotesco.digital_bank.models.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    public Transaction toTransaction(TransactionRequestDTO request) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        return transaction;
    }

    public TransactionResponseDTO toTransactionResponseDTO(Transaction transaction, String accountId) {
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setTimestamp(LocalDateTime.now());
        response.setAccountId(accountId);
        return response;
    }
}
