package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


import com.techelevator.tenmo.models.Transfer;


public class TransferService {
	
	public static String AUTH_TOKEN = ""; 
	private String BASE_URL;
	private RestTemplate restTemplate= new RestTemplate();

	public TransferService(String url) {
		this.BASE_URL = url;
	}
	
	
	public List<Transfer> viewTransfers(long id) throws TransferServiceException {
		List<Transfer> transfers = new ArrayList<>();
//		Transfer[] transfers = null;
			
		try {
//			transfers = restTemplate.getForEntity(BASE_URL + "transfers" + "?userId=" + id, HttpMethod.GET, makeAuthEntity(), Transfer[].class);
			
			Transfer[] transferArray = 
					restTemplate.exchange(BASE_URL + "transfers" + "?userId=" + id, HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
						for(Transfer t : transferArray) {
						transfers.add(t);
						}
			
		} catch (RestClientResponseException ex) {
			throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		return transfers;
	}
	
	public Transfer viewTransferById(int transferId) throws TransferServiceException {
		Transfer transfer = null;
		try { 
			transfer = restTemplate.exchange(BASE_URL + 
					"transfers/" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		return transfer;
	}
	
	
	public boolean sendBucks(int accountFrom, int accountTo, BigDecimal amount) throws TransferServiceException {
		//check that account balance is great than transfer amount
		boolean canSendBucks = false;
		Transfer transfer = new Transfer();
		
		try {
		      canSendBucks = restTemplate
              .exchange(BASE_URL + "transfers" + "?accountFrom=" + accountFrom + "&accountTo=" + accountTo + 
            		  "&amount=" + amount, HttpMethod.POST, makeTransferEntity(transfer), boolean.class)
              .getBody();
			
		} catch (RestClientResponseException ex) {
			throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());		
		}
		return canSendBucks;
	}
	
    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }


    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
	
}
