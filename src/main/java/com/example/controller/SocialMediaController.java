package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;


@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }

    @GetMapping(value = "/messages/{message_id}")
    public ResponseEntity<Message> getMessageByid(@PathVariable int message_id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageByid(message_id));
    }

    @GetMapping(value = "/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountid(@PathVariable int account_id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessagesByAccountid(account_id));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account requestBody){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.registerAccount(requestBody));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account requestBody){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.loginAccount(requestBody));
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message requestBody){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.postMessage(requestBody));
    }

    @PatchMapping(value = "/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageByid(@PathVariable int message_id, @RequestBody Message requestBody){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.updateMessageByid(message_id, requestBody));
    }

    @DeleteMapping(value = "/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageByid(@PathVariable int message_id){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.deleteMessageByid(message_id));
    }

}
