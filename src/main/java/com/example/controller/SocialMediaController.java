package com.example.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account requestBody){
        return accountService.registerAccount(requestBody);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account requestBody){
        return accountService.loginAccount(requestBody);
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message requestBody){
        return messageService.postMessage(requestBody);
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return messageService.getAllMessages();
    }

    @GetMapping(value = "/messages/{message_id}")
    public ResponseEntity<Message> getMessageByid(@PathVariable int message_id){
        return messageService.getMessageByid(message_id);
    }

    @DeleteMapping(value = "/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageByid(@PathVariable int message_id){
        return messageService.deleteMessageByid(message_id);
    }

}
