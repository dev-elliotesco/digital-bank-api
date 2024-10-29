package com.dev_elliotesco.digital_bank.repositories;

import com.dev_elliotesco.digital_bank.models.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepository extends ReactiveMongoRepository<Account, String>{
}
