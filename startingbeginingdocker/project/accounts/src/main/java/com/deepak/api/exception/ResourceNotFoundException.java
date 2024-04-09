package com.deepak.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND) 
public class ResourceNotFoundException extends Exception {
	
	public ResourceNotFoundException(String resourceName, String fieldName, String mobileNumber) {
		super(String.format("%s not found with the input data %s : '%s'",resourceName,fieldName,mobileNumber));

	}

}
