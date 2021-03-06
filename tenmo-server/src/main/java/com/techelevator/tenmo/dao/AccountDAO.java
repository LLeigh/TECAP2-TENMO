package com.techelevator.tenmo.dao;

import java.math.BigDecimal;


public interface AccountDAO {

	
	BigDecimal getBalance(long userId);
	
	void updateBalance(BigDecimal amount, int userId);
	
	int getAccountId(long userId);

}
