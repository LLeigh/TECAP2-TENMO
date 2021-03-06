package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.techelevator.tenmo.dao.AccountDAO;


@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/accounts")
public class AccountController {

	
	private AccountDAO dao;
	
	public AccountController(AccountDAO accountDao) {
		this.dao = accountDao;
	}
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public BigDecimal getBalance(@RequestParam long userId) {
		return dao.getBalance(userId);
	};
	
	@RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
	public void updateBalance(@RequestParam BigDecimal amount, @PathVariable int userId) throws AccountNotFoundException{
		dao.updateBalance(amount, userId);
	};
	
	@RequestMapping(path = "/find", method = RequestMethod.GET)
	public int getAccountId(long userId) {
		return dao.getAccountId(userId);
		
	}
	

}
