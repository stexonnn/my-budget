package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;




@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	
	 @ExceptionHandler(InvalidRegisterException.class)
	    public ResponseEntity<Object> handleInvalidRegistrationException(InvalidRegisterException ex) {
	    		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
	           return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);  
	    	
	    }
	 
	 @ExceptionHandler(InvalidAccountException.class)
	    public ResponseEntity<Object> handleInvalidAccountException(InvalidAccountException ex) {
	    		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
	           return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);  
	    	
	    }
	 
	 @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleNotFoundException(UserNotFoundException ex) {
	        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	    }
	 
	 
}
