package com.techelevator.tenmo.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	
	AccountDAO dao;
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public BigDecimal getBalance(@RequestParam long userId) {
		return dao.getBalance(userId);
	};
	
	@RequestMapping(path = "", method = RequestMethod.PUT)
	public void updateBalance(@RequestParam BigDecimal amount, @RequestParam int fromUser, @RequestParam int toUser) throws AccountNotFoundException{
		
	};
}
