package com.dev_elliotesco.digital_bank.repositories;

import com.dev_elliotesco.digital_bank.models.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
}
