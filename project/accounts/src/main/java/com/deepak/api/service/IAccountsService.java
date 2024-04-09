package com.deepak.api.service;

import com.deepak.api.dto.CustomerDto;

public interface IAccountsService {
	
	public void createCustomer(CustomerDto customerDto) throws Exception;
	
	public CustomerDto fetchAccountDetails(String mobileNumber) throws Exception;
	
	public boolean updateAccount(CustomerDto customerDto) throws Exception;
	
	public boolean deleteAccount(String mobileNumber) throws Exception;


}
