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
import com.techelevator.tenmo.models.User;


public class TransferService {
	
	public static String AUTH_TOKEN = ""; 
	private String BASE_URL;
	private RestTemplate restTemplate= new RestTemplate();

	public TransferService(String url) {
		this.BASE_URL = url;
	}
	
	
	public List<Transfer> viewTransfers(long id) throws TransferServiceException {
		List<Transfer> transferList = new ArrayList<>();
			
		try {

			Transfer[] transferArray = 
					restTemplate.exchange(BASE_URL + "transfers/" + id, HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
						for(Transfer t : transferArray) {
						transferList.add(t);
						}
			for(Transfer t : transferArray) {
				transferList.add(t);
						
			}	 
		} catch (RestClientResponseException ex) {
			throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		return transferList;
	}
	
	public Transfer viewTransferById(int transferId) throws TransferServiceException {
		Transfer transfer = null;
		try { 
			transfer = restTemplate.exchange(BASE_URL + 
					"transfers?transferId=" + transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
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
              .exchange(BASE_URL + "/transfers" + "?accountFrom=" + accountFrom + "&accountTo=" + accountTo + 
            		  "&amount=" + amount, HttpMethod.POST, makeTransferEntity(transfer), boolean.class)
              .getBody();
			
		} catch (RestClientResponseException ex) {
			throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());		
		}
		if (!canSendBucks) {
			System.out.println("/n" + "Insufficient funds.");
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
