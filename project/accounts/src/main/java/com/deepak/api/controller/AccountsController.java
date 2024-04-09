package com.deepak.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.api.constants.AccountsConstants;
import com.deepak.api.dto.AccountsDto;
import com.deepak.api.dto.CustomerDto;
import com.deepak.api.dto.ResponseDto;
import com.deepak.api.service.IAccountsService;
import com.deepak.api.service.impl.AccountsServiceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
@Validated
public class AccountsController {

	private IAccountsService iAccountsService;

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) throws Exception {

		iAccountsService.createCustomer(customerDto);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

	}

	@GetMapping("/fetchByMobileNumber")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam 
			@Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits") String mobileNumber) throws Exception {
		CustomerDto customerDto = iAccountsService.fetchAccountDetails(mobileNumber);

		return ResponseEntity.status(HttpStatus.OK).body(customerDto);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) throws Exception{

	boolean isUpdated = iAccountsService.updateAccount(customerDto);
	if(isUpdated){
	return ResponseEntity
	 .status(HttpStatus.OK)
	 .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200)); 
	}
	else{
	return ResponseEntity
	 .status(HttpStatus.INTERNAL_SERVER_ERROR)
	 .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
	}}
	
	@DeleteMapping("/delete")	
	public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam 
			@Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits") String mobileNumber) throws Exception{
		 boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
		 if(isDeleted) {
			 
			 return ResponseEntity
					 .status(HttpStatus.OK)
					 .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
		 }
		 
		 else {
			 return ResponseEntity
					 .status(HttpStatus.INTERNAL_SERVER_ERROR)
					 .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
					}
		 }
	}


