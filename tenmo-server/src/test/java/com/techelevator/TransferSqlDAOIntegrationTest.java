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


import com.techelevator.tenmo.dao.TransferSqlDAO;

public class TransferSqlDAOIntegrationTest {
	private static SingleConnectionDataSource dataSource;
	private TransferSqlDAO dao;
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
		dao = new TransferSqlDAO();
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	

}
