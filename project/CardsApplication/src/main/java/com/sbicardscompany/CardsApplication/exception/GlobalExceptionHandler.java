package com.sbicardscompany.CardsApplication.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.deepak.api.dto.ErrorResponseDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends Throwable {
	
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
	 
	  @ExceptionHandler(CardAlreadyExistsException.class)
	    public ResponseEntity<ErrorResponseDto> handleCardAlreadyExistsException(CardAlreadyExistsException exception,
	                                                                          WebRequest webRequest){
	        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
	                webRequest.getDescription(false),
	                HttpStatus.BAD_REQUEST,
	                exception.getMessage(),
	                LocalDateTime.now()
	        );
	        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
	  }
	  
	 
	   @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
	                                                                            WebRequest webRequest) {
	        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
	                webRequest.getDescription(false),
	                HttpStatus.NOT_FOUND,
	                exception.getMessage(),
	                LocalDateTime.now()
	        );
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
	    }


	}



