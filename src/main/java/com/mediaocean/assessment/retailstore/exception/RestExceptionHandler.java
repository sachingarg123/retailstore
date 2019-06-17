package com.mediaocean.assessment.retailstore.exception;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	final Logger logger = LoggerFactory.getLogger(getClass());

	

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(Exception rnfe, HttpServletRequest request) {
		ExceptionResponse errorDetail = new ExceptionResponse(rnfe.getClass().getName(),new Date(),rnfe.getMessage());
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);

	}

}
