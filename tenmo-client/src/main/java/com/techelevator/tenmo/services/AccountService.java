package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import com.techelevator.tenmo.models.Account;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;


public class AccountService {
	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	
	  
	  public AccountService(String url) {
		    BASE_URL = url;
		  }
	 
	  public BigDecimal getBalance (long userId) throws AccountServiceException {
		    BigDecimal balance = null;
		    try {
		      balance = restTemplate
		              .exchange(BASE_URL + "accounts" + "?userId=" + userId, HttpMethod.GET, makeAuthEntity(), BigDecimal.class)
		              .getBody();
		      
		   
		     
		    } catch (RestClientResponseException e) {
		      throw new AccountServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
		    }
		    return balance;
	  }
	  
	  public void updateBalance(BigDecimal amount, int userId) throws AccountServiceException{
		  
		  Account account = new Account();
		  try {
			      restTemplate
			              .exchange(BASE_URL + "accounts/" + userId + "?amount=" + amount, HttpMethod.PUT, makeAccountEntity(account), Account.class)
			             
			              .getBody();
			    } catch (RestClientResponseException e) {
			      throw new AccountServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
			    }
			   
	  }
	  
	  public int getAccountId(long userId) throws AccountServiceException{
		  int accountId = 0;
		  try {
		      accountId = restTemplate
		              .exchange(BASE_URL + "accounts/find" + "?userId=" + userId, HttpMethod.GET, makeAuthEntity(), Integer.class)
		              .getBody();
		    } catch (RestClientResponseException e) {
		      throw new AccountServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
		    }
		  
		  return accountId;
	  }
	  
	  
	//Helper Method
	  private HttpEntity<Account> makeAccountEntity(Account account) {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity<Account> entity = new HttpEntity<>(account, headers);
		    return entity;
		  }
	//Helper Method
	  private HttpEntity makeAuthEntity() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity entity = new HttpEntity<>(headers);
		    return entity;
		  }
}
