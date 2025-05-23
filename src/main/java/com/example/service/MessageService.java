package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.customexceptions.GeneralException;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

     //obtain all messages from database. always returns 200 OK
     public List<Message> getAllMessages(){
        List<Message> messages = (List<Message>) messageRepository.findAll(); 

        return messages;
    }

     //obtain message by its id, body can be empty if no message. always returns 200 OK
     public Message getMessageByid(int id){
        Optional<Message> message = messageRepository.findById(id);
        
        if (message.isPresent()){
            return message.get();
        }
        else{
            return null;
        }
    }

    //Retrieve all messages from a user given their Account id
    public List<Message> getMessagesByAccountid(int accountid){
        List<Message> messages = messageRepository.findBypostedBy(accountid);

        return messages;
    }

    //save new messages and on success return 200 OK with saved JSON message, restrictions are
    //messageText not blank/null, < 255 characters and posted by a real existing user. if failed 400 Client error
    public Message postMessage(Message message){
        if (message.getMessageText() != null){
            if (message.getMessageText().length() < 255 && message.getMessageText().length() > 0){
                Optional<Account> account = accountRepository.findById(message.getPostedBy());

                if (account.isPresent()){
                    return messageRepository.save(message);
                }
                else{
                    throw new GeneralException("Error: Not valid account");
                }
            }
            else{
                throw new GeneralException("Error: Uploaded message must be a valid character length");
            }
        }
        else{
            throw new GeneralException("Error: Uploaded message must contain text");
        } 
    }

    //update a message given an id and message_text. return 200 OK with number of rows affected
    //if any error occurs response will be 400 Client error
    public Integer updateMessageByid(int id, Message message){
        if (message.getMessageText() != null){
            if (message.getMessageText().length() > 0 && message.getMessageText().length() < 255){
                Optional<Message> existingMessage = messageRepository.findById(id);

                if (existingMessage.isPresent()){
                    Message updatedMessage = existingMessage.get();
                    updatedMessage.setMessageText(message.getMessageText());
                    messageRepository.save(updatedMessage);
                    return 1;
                }
                else{
                    throw new GeneralException("Error: Message does not exist");
                }
            }
            else{
                throw new GeneralException("Error: Message must be a valid character length");
            }
        }
        else{
            throw new GeneralException("Error: Message must contain text");
        }
    }

    //delete a message, response should always be 200 OK. returns either empty body or 1 row affected
    public Integer deleteMessageByid(int id){
        Optional<Message> message = messageRepository.findById(id);

        if (message.isPresent()){
            messageRepository.deleteById(id);
            return 1;
        }
        else{
            return null;
        }
    }

}
