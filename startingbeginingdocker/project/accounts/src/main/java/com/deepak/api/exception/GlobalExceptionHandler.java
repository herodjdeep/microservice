package com.deepak.api.exception;



import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.deepak.api.dto.ErrorResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	   @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
	    public ResponseEntity<Map<String, String>> handleValidationException(Exception ex) {
	        Map<String, String> errors = new HashMap<>();

	        if (ex instanceof MethodArgumentNotValidException) {
	            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
	            bindingResult.getAllErrors().forEach(error -> {
	                String fieldName = ((FieldError) error).getField();
	                String errorMessage = error.getDefaultMessage();
	                errors.put(fieldName, errorMessage);
	            });
	        } else if (ex instanceof BindException) {
	            BindingResult bindingResult = ((BindException) ex).getBindingResult();
	            bindingResult.getAllErrors().forEach(error -> {
	                String fieldName = ((FieldError) error).getField();
	                String errorMessage = error.getDefaultMessage();
	                errors.put(fieldName, errorMessage);
	            });
	        }

	        return ResponseEntity.badRequest().body(errors);
	    }
	


	@ExceptionHandler(Exception.class)
 public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest){
	 ErrorResponseDto errorResponseDto = new ErrorResponseDto(
			 webRequest.getDescription(false),
			 HttpStatus.INTERNAL_SERVER_ERROR,
			 exception.getMessage(),
			 LocalDateTime.now()
			 );
	 
	 return new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
 
	

	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(
			CustomerAlreadyExistsException customerAlreadyExistsException, WebRequest webRequest) {

		//ErrorResponseDto errorResponseDto = new ErrorResponseDto(webRequest.getDescription(false),
				//HttpStatus.BAD_REQUEST, customerAlreadyExistsException.getMessage(), LocalDateTime.now());

		//return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponseDto( webRequest.getDescription(false),HttpStatus.BAD_REQUEST, customerAlreadyExistsException.getMessage(), 
						LocalDateTime.now()));
	}
	
	
	public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
			ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
		
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponseDto(webRequest.getDescription(false),
						HttpStatus.NOT_FOUND,resourceNotFoundException.getMessage(),LocalDateTime.now()));
	}

}
