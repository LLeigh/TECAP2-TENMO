package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;

@Component
public class TransferSqlDAO implements TransferDAO {
	
	private JdbcTemplate jdbcTemplate;

	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Transfer> viewTransfers(long id){
		List<Transfer> userTransfers = new ArrayList<>();
		
		String sqlGetUserTransfers = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " + 
				"FROM transfers " + 
				"JOIN accounts ON accounts.account_id = transfers.account_from " + 
				"JOIN users USING (user_id) " + 
				"WHERE transfers.account_from = ? OR transfers.account_to = ?;";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetUserTransfers, id, id);
		while(results.next()) {
			Transfer transfer = mapRowToTransfer(results);
			userTransfers.add(transfer);
		}
		
		return userTransfers;
	}
	
	@Override
	public Transfer viewTransferById(int transferId) {
		
		String sqlGetTransfer = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " + 
				"FROM transfers " + 
				"WHERE transfer_id = ?;";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetTransfer, transferId);
		Transfer transfer = mapRowToTransfer(results);
		return transfer;
	}
	
	@Override
	public boolean sendBucks(int accountFrom, int accountTo, BigDecimal amount) {//subtracts from 'from user', adds to 'to user'
		boolean bucksSent = false;

		String sqlCreateSendTransfer = "INSERT INTO transfers " +
				"(transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
				"VALUES (2, 2, ?, ?, ?);";
		
		bucksSent = jdbcTemplate.update(sqlCreateSendTransfer, accountFrom, accountTo, amount) == 1;
		
		return bucksSent;
	}

	
	private Transfer mapRowToTransfer(SqlRowSet rs) {
		Transfer transfer = new Transfer();
		transfer.setTransferId(rs.getInt("transfer_id"));
		transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
		transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
		transfer.setAccountFrom(rs.getInt("account_from"));
		transfer.setAccountTo(rs.getInt("account_to"));
		transfer.setAmount(rs.getBigDecimal("amount"));
		
		return transfer;
	}
}
