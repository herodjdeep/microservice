package com.deepak.api.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.deepak.api.constants.AccountsConstants;
import com.deepak.api.dto.AccountsDto;
import com.deepak.api.dto.CustomerDto;
import com.deepak.api.entity.Accounts;
import com.deepak.api.entity.Customer;
import com.deepak.api.exception.CustomerAlreadyExistsException;
import com.deepak.api.exception.ResourceNotFoundException;
import com.deepak.api.mapper.AccountsMapper;
import com.deepak.api.mapper.CustomerMapper;
import com.deepak.api.repository.AccountsRepository;
import com.deepak.api.repository.CustomerRepository;
import com.deepak.api.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;

	@Override
	public void createCustomer(CustomerDto customerDto) throws Exception { // controller layer

		Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

		if (optionalCustomer.isPresent()) {

			throw new CustomerAlreadyExistsException(
					"Customer already exists with mobile number " + customerDto.getMobileNumber());

			// will go to the created Exception package
		}

		Customer newCustomer = CustomerMapper.mapToCustomer(customerDto, new Customer());

		Customer savedCustomer = customerRepository.save(newCustomer);
		Accounts savedAccounts = accountsRepository.save(createUserAccounts(savedCustomer));

	}

	public Accounts createUserAccounts(Customer savedcustomers) { // carries saved data of customers

		Accounts account = new Accounts();

		account.setCustomerId(savedcustomers.getCustomerId());
		long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
		account.setAccountNumber(randomAccountNumber);
		account.setAccountType(AccountsConstants.SAVINGS);
		account.setBranchAddress(AccountsConstants.ADDRESS);
	
		return account;

	}

	@Override
	public CustomerDto fetchAccountDetails(String mobileNumber) throws Exception {

		Customer customer = customerRepository.findByMobileNumber(mobileNumber)

				.orElseThrow(() -> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber));

		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())

				.orElseThrow(() -> new ResourceNotFoundException("Account Details", "customer Id",
						String.valueOf(customer.getCustomerId()))
						
						);

		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());

		
		AccountsDto accountDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
		customerDto.setAccountsDto(accountDto);
		return customerDto;

	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) throws Exception {
		
		boolean isUpdated = false;
		
		AccountsDto accountsDto = customerDto.getAccountsDto();
		
		if(accountsDto!=null) {
			
			Accounts accounts = accountsRepository.findById((int)accountsDto.getAccountNumber())
				.orElseThrow(() -> new ResourceNotFoundException("Account","AccountNumber",
						String.valueOf(accountsDto.getAccountNumber())) 	
					);
			
			 AccountsMapper.mapToAccounts(accountsDto,accounts);
			accounts = accountsRepository.save(accounts);
			
			int customerId = accounts.getCustomerId();
			Customer customer = customerRepository.findById(customerId)
					.orElseThrow(
			()-> new ResourceNotFoundException("Customer","CustomerId",String.valueOf(customerId))
			);
			
			CustomerMapper.mapToCustomer(customerDto,customer);
			customerRepository.save(customer);
			isUpdated =true;
			}
		
			return isUpdated;
			
	}

	@Override
	public boolean deleteAccount(String mobileNumber) throws Exception {
		
		Customer customer = customerRepository.findByMobileNumber(mobileNumber)
		 .orElseThrow(
				 () -> new ResourceNotFoundException("customer","mobile number",mobileNumber) 
				 
				 );
		accountsRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		
		return true;
	}

}
