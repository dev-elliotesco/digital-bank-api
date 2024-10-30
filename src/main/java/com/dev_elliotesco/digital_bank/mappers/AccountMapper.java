package com.dev_elliotesco.digital_bank.mappers;

import com.dev_elliotesco.digital_bank.dtos.AccountRequestDTO;
import com.dev_elliotesco.digital_bank.dtos.AccountResponseDTO;
import com.dev_elliotesco.digital_bank.models.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toAccount(AccountRequestDTO accountRequest) {
        Account account = new Account();
        account.setNumber(accountRequest.getNumber());
        account.setType(accountRequest.getType());
        return account;
    }

    public AccountResponseDTO toAccountResponseDTO(Account account) {
        AccountResponseDTO response = new AccountResponseDTO();
        response.setId(account.getId());
        response.setNumber(account.getNumber());
        response.setType(account.getType());
        return response;
    }
}
