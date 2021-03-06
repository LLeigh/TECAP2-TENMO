package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface AccountDAO {

	
	BigDecimal getBalance(long userId);
	void updateBalance(BigDecimal amount, int userId);
	
	int getAccountId(long userId);

}
