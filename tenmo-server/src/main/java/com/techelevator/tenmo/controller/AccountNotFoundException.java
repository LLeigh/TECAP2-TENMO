package com.techelevator.tenmo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.NOT_FOUND, reason = "Account Not Found")
public class AccountNotFoundException extends Exception {

	
    public AccountNotFoundException(){
        super("Account Not Found");
    }
}
