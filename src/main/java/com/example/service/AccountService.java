package com.example.service;

import com.example.entity.Account;
import com.example.exception.customexceptions.DuplicateException;
import com.example.exception.customexceptions.GeneralException;
import com.example.exception.customexceptions.UnauthorizedException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //Account registration, return JSON of persisted account, else 409 for duplicate, 400 for all else
    //valid account has not empty/null username and password + password length >= 4
    public Account registerAccount(Account account){
        if (account.getUsername() != null && account.getPassword() != null){
            if (account.getUsername().length() > 0 && account.getPassword().length() >= 4){
                Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());

                if (existingAccount.isEmpty()){
                    return accountRepository.save(account);
                }
                else{
                    throw new DuplicateException("Error: Account already exists");
                }
            }
            else{
                throw new GeneralException("Error: Client request must include username and passwords of valid lengths");
            }
        }
        else{
            throw new GeneralException("Error: Client request must include a valid username and password");
        }
        
    }

    //Account login should return 200 OK if successful or else 401 Unauthorized if does not match
    public Account loginAccount(Account account){
        Optional<Account> verifiedAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());

        if (verifiedAccount.isPresent()){
            return verifiedAccount.get();
        }
        else{
            throw new UnauthorizedException("Error: Login invalid");
        }
    }
}
