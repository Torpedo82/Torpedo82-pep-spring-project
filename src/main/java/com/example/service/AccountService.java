package com.example.service;

import com.example.entity.Account;
import com.example.exception.customexceptions.DuplicateException;
import com.example.exception.customexceptions.GeneralException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //Account registration, return JSON of persisted account, else 409 for duplicate, 400 for all else
    //valid account has not empty/null username and password + password length >= 4
    public ResponseEntity<Account> registerAccount(Account account){
        if (account.getUsername() != null && account.getPassword() != null 
            && account.getUsername().length() > 0 && account.getPassword().length() >= 4){
            
                Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());

                if (existingAccount.isEmpty()){
                    Account savedAccount = accountRepository.save(account);
                    return ResponseEntity.status(HttpStatus.OK).body(savedAccount);
                }
                else{
                    throw new DuplicateException("Error: Account already exists");
                }
            }
        else{
            throw new GeneralException("Error: Client request must include a valid username and password");
        }
        
    }
}
