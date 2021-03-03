package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public class TransferSqlDAO implements TransferDAO {
	
	@Override
	public List<Transfer> viewTransfers(){
		return null;
	}
	
	@Override
	public double sendBucks() {//subtracts from 'from user', adds to 'to user'
		return 0.0;
	}
}
