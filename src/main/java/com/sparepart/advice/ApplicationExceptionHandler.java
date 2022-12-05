package com.sparepart.advice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sparepart.exception.CanNotUpdateBrandNameException;
import com.sparepart.exception.WrongInputException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach( error -> {
			errors.put(error.getField(), error.getDefaultMessage());
		});
		return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(err ->{
			errors.put(err.getPropertyPath().toString(), err.getMessage());
		});
		return errors;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({BadCredentialsException.class, IllegalArgumentException.class, WrongInputException.class, EmptyResultDataAccessException.class, HttpMessageNotReadableException.class, CanNotUpdateBrandNameException.class})
	public Map<String, String> handleInputRelatedException(Exception ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("message",ex.getMessage());
		return errors;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NoSuchElementException.class})
	public Map<String, String> handleResourceNotFoundException(Exception ex) {
		System.out.println("Here ########");
		Map<String, String> errors = new HashMap<>();
		errors.put("message",ex.getMessage());
		return errors;
	}
	
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler({SQLIntegrityConstraintViolationException.class})
	public Map<String, String> handleSQLIntegrityConstraintViolationException(Exception ex) {
		Map<String, String> errors = new HashMap<>();
		errors.put("message","Cascade delete: Please delete child entities first");
		return errors;
	}
}
