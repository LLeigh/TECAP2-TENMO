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

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.AccountSqlDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;

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
		String userOne = "INSERT INTO accounts (user_id, balance) VALUES (22, 5000)";
		jdbcTemplate.update(userOne);
		
		String userTwo = "INSERT INTO accounts (user_id, balance) VALUES (33, 1)";
		jdbcTemplate.update(userTwo);
		
		BigDecimal amount = new BigDecimal(200);
		
		
		dao.updateBalance(amount, 22, 33);
		
		BigDecimal total = dao.getBalance(22);
		
		assertEquals(4800, total);

	}
	
	@Test
	public void does_transfer_update_to_user_balance() {
		String userOne = "INSERT INTO accounts (user_id, balance) VALUES (22, 5000)";
		jdbcTemplate.update(userOne);
		
		String userTwo = "INSERT INTO accounts (user_id, balance) VALUES (33, 1)";
		jdbcTemplate.update(userTwo);
		
		BigDecimal amount = new BigDecimal(200);
		
		
		dao.updateBalance(amount, 22, 33);
		
		BigDecimal total = dao.getBalance(33);
		
		assertEquals(201, total);

	}

}
