package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.UsernameAlreadyUsedException;
import com.example.repository.AccountRepository;

import javassist.tools.web.BadHttpRequest;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account) throws BadHttpRequest, UsernameAlreadyUsedException {
        if (account.getUsername().isEmpty() || 
            account.getPassword().length() < 4) 
        {
            throw new BadHttpRequest();
        }

        // FIND BY USERNAME NOT ID
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new UsernameAlreadyUsedException();
        }

        return accountRepository.save(account);
    }
}
