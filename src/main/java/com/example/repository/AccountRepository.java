package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>{
    Optional<Account> findByUsername(String username);
}
