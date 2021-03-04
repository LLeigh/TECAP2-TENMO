package com.techelevator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


import com.techelevator.tenmo.dao.TransferSqlDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.Transfer;

import junit.framework.Assert;

public class TransferSqlDAOIntegrationTest {
	private static SingleConnectionDataSource dataSource;
	private TransferSqlDAO dao;
	private JdbcTemplate jdbcTemplate;
	private UserSqlDAO daoUser;
	
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
		jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new TransferSqlDAO(jdbcTemplate);
		daoUser = new UserSqlDAO(jdbcTemplate);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void viewTransfers_returns_transfer_by_userid() {
		//Arrange
		
		//add 2 users to test
		daoUser.create("testuser1","test123");
		daoUser.create("testuser2","test321");
		
		//get userIds
		int idUser1 = daoUser.findIdByUsername("testuser1");
		int idUser2 = daoUser.findIdByUsername("testuser2");
		
		//get accountIds - assume userId and accountId are same?
		
		//set up transfersList for user1 before new transfer
		List<Transfer> user1Transfers = dao.viewTransfers(idUser1);
		int user1TransfersBefore = user1Transfers.size();	
		
		//Act
		//add transfer with those users
		BigDecimal testAmount = new BigDecimal("50.00");
		dao.sendBucks(idUser1, idUser2, testAmount);
		List<Transfer> user1TransfersNow = dao.viewTransfers(idUser1);
		int user1TransfersAfter = user1TransfersNow.size();
		
		//test get transferbyTransferId
		Transfer testTransfer = user1TransfersNow.get(0);
		//int testTransferId = testTransfer.getTransferId();
		
		BigDecimal expected = new BigDecimal("50.00");
		
		//Assert
		//check list size and get balance?
		assertEquals(user1TransfersBefore + 1, user1TransfersAfter);
		assertEquals(expected, testTransfer.getAmount());
	}
	
	@Test
	public void viewTransferById_happy_path() {
		
	}
	
	@Test 
	public void sendBucks_creates_new_transfer() {
		
	}


}
