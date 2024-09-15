package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import javassist.tools.web.BadHttpRequest;

@Service
public class MessageService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) throws BadHttpRequest {
        if (message.getMessageText().isEmpty() || 
            message.getMessageText().length() > 255 ||
            accountRepository.findById(message.getPostedBy()).isEmpty()) 
        {
            throw new BadHttpRequest();
        }
        return messageRepository.save(message);
    }
}
