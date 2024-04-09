package com.deepak.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST) 
public class CustomerAlreadyExistsException extends Exception {

	public CustomerAlreadyExistsException(String msg){
		super(msg);
	}
	
}
