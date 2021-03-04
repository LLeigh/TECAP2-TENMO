package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.AccountSqlDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.User;

public class AccountSqlDAOIntegrationTest {
	
	private static SingleConnectionDataSource dataSource;
	private AccountSqlDAO dao;
	private JdbcTemplate jdbcTemplate;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		dao = new AccountSqlDAO(dataSource);
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void gets_correct_balance() {

		
		String userOne = "INSERT INTO users (user_id, username, password_hash) VALUES (22, 'Ellen', 'tebucks')";
		jdbcTemplate.update(userOne);
		
		String accountOne = "INSERT INTO accounts (user_id, balance) VALUES (22, 5000)";
		jdbcTemplate.update(accountOne);
		
		BigDecimal actual = dao.getBalance(22);
		
		BigDecimal expected = new BigDecimal("5000.00");
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void does_transfer_update_from_user_balance() {
		
		String userOne = "INSERT INTO users (username, password_hash) VALUES ('Bob', 'tebucks')";
		jdbcTemplate.update(userOne);
		
		String accountOne = "INSERT INTO accounts (user_id, balance) VALUES ((SELECT user_id FROM users WHERE username = 'Bob'), 5000)";
		jdbcTemplate.update(accountOne);
		
		String statement = "SELECT user_id FROM users WHERE username = 'Bob'";
		SqlRowSet oneRow = jdbcTemplate.queryForRowSet(statement);
		oneRow.next();
		int id = oneRow.getInt("user_id");
		
		BigDecimal amount = new BigDecimal(200);
		
		BigDecimal actualBefore = dao.getBalance(id);
		BigDecimal expectedBefore = new BigDecimal("5000.00");
		assertEquals(expectedBefore, actualBefore);
		
		dao.updateBalance(amount, id);
		
		BigDecimal actualAfter = dao.getBalance(id);
		BigDecimal expectedAfter = new BigDecimal("5200.00");
		assertEquals(expectedAfter, actualAfter);

	}
	
	@Test
	public void does_create_user_set_balance_to_1000() {
		User user = new User();
		UserSqlDAO userDAO = new UserSqlDAO(jdbcTemplate);
		
		userDAO.create("Oprah", "password");
		
		String statement = "SELECT user_id FROM users WHERE username = 'Oprah'";
		SqlRowSet oneRow = jdbcTemplate.queryForRowSet(statement);
		oneRow.next();
		int id = oneRow.getInt("user_id");
		
		BigDecimal amount = new BigDecimal(200);
		
		BigDecimal actual = dao.getBalance(id);
		BigDecimal expected = new BigDecimal("1000.00");
		assertEquals(expected, actual);
	}
	

}
