package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.TransferSqlDAO;
import com.techelevator.tenmo.model.Transfer;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/transfers")
public class TransferController {

		private TransferDAO dao;
		
		public TransferController(TransferDAO transferDao) {
			this.dao = transferDao;
		}
		
		
		@RequestMapping (path = "/transfers/{id}", method = RequestMethod.GET)
		public List<Transfer> viewTransfers(@PathVariable long id){
			
			List<Transfer> transfersById = dao.viewTransfers(id);
			
			return transfersById;
			
		}
		
		@RequestMapping (path = "/transfers/{transferId}", method = RequestMethod.GET)
		public Transfer viewTransferById(@PathVariable int transferId) {
			
			Transfer transfer = dao.viewTransferById(transferId);
			
			return transfer;
		}
		
		@ResponseStatus(HttpStatus.CREATED)
		@RequestMapping (path = "/transfers", method = RequestMethod.POST)
		public boolean sendBucks(int accountFrom, int accountTo, BigDecimal amount) {
			
			return null;
			
		}
		

	
}
