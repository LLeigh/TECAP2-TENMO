package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/transfers")
public class TransferController {

		private TransferDAO dao;
		private AccountDAO daoAccount;
		
		public TransferController(TransferDAO transferDao, AccountDAO daoAccount) {
			this.dao = transferDao;
			this.daoAccount = daoAccount;
		}
		
		
		@RequestMapping (path = "/{id}", method = RequestMethod.GET)
		public List<Transfer> viewTransfers(@PathVariable long id){	
			return dao.viewTransfers(id);			
		}
		
		@RequestMapping (path = "/{transferId}", method = RequestMethod.GET)
		public Transfer viewTransferById(@PathVariable int transferId) {
			return dao.viewTransferById(transferId);

		}
		
		@ResponseStatus(HttpStatus.CREATED)
		@RequestMapping (path = "", method = RequestMethod.POST)
		public boolean sendBucks(@RequestParam int accountFrom, @RequestParam int accountTo, @RequestParam BigDecimal amount) {	
			long userId = (int)accountFrom;
			
			if (amount.compareTo(daoAccount.getBalance(userId)) <= 0) { 
			return dao.sendBucks(accountFrom, accountTo, amount);
			} 
			return false;
		}
		

	
}
