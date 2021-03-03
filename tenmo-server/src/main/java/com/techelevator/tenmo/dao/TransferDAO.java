package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	
	List<Transfer> viewTransfers();
	double sendBucks();//subtracts from 'from user', adds to 'to user' (use updateBalance from Account DAO) - wait on test
}
