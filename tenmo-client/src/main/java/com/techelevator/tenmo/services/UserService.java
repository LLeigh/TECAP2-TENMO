package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;



public class UserService {
	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	
	  public UserService(String url) {
		    BASE_URL = url;
		  }
	  
	  public List<User> findAll() throws UserServiceException {
		  
		  List<User> userList = new ArrayList<>();
		  
		  try {
			      User [] list = restTemplate
			              .exchange(BASE_URL + "user/all", HttpMethod.GET, makeUserEntity(), User [].class)
			              .getBody();
			      for(User u : list) {
						userList.add(u);
					
						}
			   
			     
			    } catch (RestClientResponseException e) {
			      throw new UserServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
			    }
			    return userList;
	  };

	  public User findByUsername(String username) throws UserServiceException {
		  User user = null;
		  
		  try {
			  user = restTemplate
	              .exchange(BASE_URL + "user/" + username, HttpMethod.GET, makeUserEntity(), User.class)
	              .getBody();
	     
		  } catch (RestClientResponseException e) {
			  throw new UserServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
		  }
		  return user;
	  };
	 

	  public int findIdByUsername(String username) throws UserServiceException {
		  int num = 0;
		  
		  try {
			  num = restTemplate
	              .exchange(BASE_URL + "user" + "?username=" + username, HttpMethod.GET, makeUserEntity(), Integer.class)
	              .getBody();
	     
		  } catch (RestClientResponseException e) {
			  throw new UserServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
		  }
		  return num;
	  };

	  public boolean create(String username, String password) throws UserServiceException {
		  boolean didItCreate = false;
		  
		  try {
			  didItCreate = restTemplate
	              .exchange(BASE_URL + "user" + "?username=" + username + "&?password=" + password, HttpMethod.POST, makeUserEntity(), boolean.class)
	              .getBody();
	     
		  } catch (RestClientResponseException e) {
			  throw new UserServiceException(e.getRawStatusCode() + " : " + e.getResponseBodyAsString());
		  }
		  return didItCreate;
	  };
	  
		//Helper Method
	  private HttpEntity<User> makeUserEntity(User user) {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity<User> entity = new HttpEntity<>(user, headers);
		    return entity;
		  }
	//Helper Method
	  private HttpEntity makeUserEntity() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity entity = new HttpEntity<>(headers);
		    return entity;
		  }
}
