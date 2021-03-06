package com.techelevator.tenmo.dao;

import java.math.BigDecimal;


import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component//Connected to interface - tells Spring that this is the implementation of the interface
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
	public void updateBalance(BigDecimal amount, int userId) {
		String addToUser = "UPDATE accounts SET balance = ((SELECT balance FROM accounts WHERE user_id = ?) + ?) WHERE user_id = ?";
		jdbcTemplate.update(addToUser, userId, amount, userId);
	}
	
	@Override
	public int getAccountId(long userId) {
		String sql = "SELECT account_id FROM account WHERE user_id = ?";
		SqlRowSet oneRow = jdbcTemplate.queryForRowSet(sql, userId);
		oneRow.next();
		long accountIdLong = oneRow.getInt("user_id");
		int accountId = (int)accountIdLong;
	
		return accountId;
	}
}