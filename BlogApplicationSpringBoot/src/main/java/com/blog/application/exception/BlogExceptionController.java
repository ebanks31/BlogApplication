package com.blog.application.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BlogExceptionController {
	private final Logger LOGGER = LoggerFactory.getLogger(BlogExceptionController.class);

	@ExceptionHandler(value = BlogException.class)
	public ResponseEntity<Object> blogException(BlogException exception) {
		LOGGER.error("Exception: {}", exception);
		LOGGER.error("Exception Message: {}", exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> genericException(BlogException exception) {
		LOGGER.error("Exception: {}", exception);
		LOGGER.error("Exception Message: {}", exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}