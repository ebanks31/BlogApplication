package com.blog.application.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Provides centralized exception handling for the application's controllers.
 *
 * <p>Exceptions handled here are logged and converted into HTTP responses so
 * controllers do not need to repeat the same response-building logic.</p>
 */
@ControllerAdvice
public class BlogExceptionAdvice {
	/** Logger used to record handled exceptions. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogExceptionAdvice.class);

	/**
	 * Handles application-specific blog exceptions.
	 *
	 * @param exception the application exception to log and return
	 * @return a not-found response containing the exception message
	 */
	@ExceptionHandler(value = BlogException.class)
	public ResponseEntity<Object> blogException(BlogException exception) {
		LOGGER.error("Exception: {}", exception);
		LOGGER.error("Exception Message: {}", exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles exceptions not covered by a more specific handler.
	 *
	 * @param exception the exception to log and return
	 * @return a not-found response containing the exception message
	 */
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> genericException(BlogException exception) {
		LOGGER.error("Exception: {}", exception);
		LOGGER.error("Exception Message: {}", exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}