package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.entity.Account;
import com.example.exception.AccountNotFoundException;
import com.example.exception.PasswordMismatchException;
import com.example.exception.UsernameAlreadyUsedException;
import com.example.service.AccountService;
import com.example.service.MessageService;

import javassist.tools.web.BadHttpRequest;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;
    
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            account = accountService.login(account);
        } catch(AccountNotFoundException accountNotFoundException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (PasswordMismatchException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        try {
            account = accountService.register(account);
        } catch (UsernameAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (BadHttpRequest e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
}
