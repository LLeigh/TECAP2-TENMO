package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class AccountSqlDAO implements AccountDAO {

	private JdbcTemplate jdbcTemplate;
	
	public AccountSqlDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public BigDecimal getBalance(long userId) {
		String getBalanceWithId = "SELECT balance FROM accounts WHERE user_id = ?";
		SqlRowSet oneRow = jdbcTemplate.queryForRowSet(getBalanceWithId, userId);
		oneRow.next();
		String balance = oneRow.getString("balance");
		BigDecimal balanceBig = new BigDecimal(balance);
		return balanceBig;
	}
	
	@Override
	public void updateBalance(BigDecimal amount, int fromUser, int toUser) {//Needs to be moved to Transfer?
		// Subtract amount to 'from user' & updates balance
		String subtractFromUser = "UPDATE accounts SET balance = ((SELECT balance FROM accounts WHERE user_id = ?) - ?) WHERE user_id = ?";
		jdbcTemplate.update(subtractFromUser, fromUser, amount, fromUser);
		//Adds amount to 'to user' & updates balance
		String addToUser = "UPDATE accounts SET balance = ((SELECT balance FROM accounts WHERE user_id = ?) + ?) WHERE user_id = ?";
		jdbcTemplate.update(addToUser, amount, toUser);
	}

}
