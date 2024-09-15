package com.example.service;

import java.util.List;
import java.util.Optional;

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

    public Message deleteMessageById(int messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            messageRepository.delete(messageOptional.get());
        }
        return messageOptional.orElse(null);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public Message updateMessageById(int messageId, Message replacement) throws BadHttpRequest {
        Optional<Message> messageOptional = messageRepository.findById(messageId);

        if (replacement.getMessageText().isEmpty() ||
            replacement.getMessageText().length() > 255 ||
            messageOptional.isEmpty()) 
        {
            throw new BadHttpRequest();
        }

        Message message = messageOptional.get();
        message.setMessageText(replacement.getMessageText());
        return messageRepository.save(message);            
    }
}
