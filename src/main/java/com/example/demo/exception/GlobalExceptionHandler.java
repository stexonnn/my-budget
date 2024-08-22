package com.example.demo.exception;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.ConstraintViolationException;


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
	 
	 @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<Object> handleInvalidAccountException(IllegalArgumentException ex) {
	    		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
	    			return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);  
	    	
	}
	 
	 @ResponseBody
	 @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleNotFoundException(UserNotFoundException ex) {
	        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	 
	 @ResponseBody
	 @ExceptionHandler(UsernameNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
		 	return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	 @ResponseBody
	 @ExceptionHandler(NoSuchElementException.class)
	    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
	        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
		 	return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	 }
	 
	 @ResponseBody
	 @ExceptionHandler(EmailNotUniqueException.class)
	    public ResponseEntity<Object> handleEmailNotUniqueException(EmailNotUniqueException ex) {
	    		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
	           return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);  
	    	
	    }
	 
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 @ResponseBody
	 public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

	     // Collect field errors
	     List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
	         .map(error -> new FieldError(error.getObjectName(),  
	                 error.getField(),        
	                 error.getRejectedValue() != null ? error.getRejectedValue().toString() : null, 
	                 false,                   
	                 error.getCodes(),        
	                 error.getArguments(),  
	                 error.getDefaultMessage()))
	         .collect(Collectors.toList());
	     
	     	System.out.println(fieldErrors.get(0).getDefaultMessage() + " "
	         +fieldErrors.get(0).getField() +" "+  fieldErrors.get(0).getRejectedValue());
	     // Create an ErrorResponse object
	     ErrorResponse errorResponse = new ErrorResponse("Validation failed", HttpStatus.BAD_REQUEST.value(), fieldErrors);

	     // Return the ResponseEntity with BAD_REQUEST status
	     return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	 }
	    
	    @ExceptionHandler(ConstraintViolationException.class)
	    @ResponseBody
	    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
	    	System.out.println("Asdasda 2231");

	    	 List<FieldError> fieldErrors = ex.getConstraintViolations().stream()
	    	            .map(violation -> new FieldError(violation.getPropertyPath().toString(), violation.getMessageTemplate(), null))
	    	            .collect(Collectors.toList());
	        
	        ErrorResponse errorResponse = new ErrorResponse("Validation failed", HttpStatus.BAD_REQUEST.value(), fieldErrors);
	        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

	    }
}
