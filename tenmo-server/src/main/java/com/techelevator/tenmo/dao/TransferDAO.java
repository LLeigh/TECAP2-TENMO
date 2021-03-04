package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	
	List<Transfer> viewTransfers(long id);
	
	Transfer viewTransferById(int transferId);
	
	boolean sendBucks(int accountFrom, int accountTo, BigDecimal amount);//subtracts from 'from user', adds to 'to user' (use updateBalance from Account DAO) - wait on test


}

