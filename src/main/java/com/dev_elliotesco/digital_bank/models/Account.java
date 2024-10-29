package com.dev_elliotesco.digital_bank.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "Accounts")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String id;
    private String number;
    private String type;
    private List<Transaction> transactions = new ArrayList<>();
}
