package com.dev_elliotesco.digital_bank.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String address;
    private List<Account> accounts;
}
