package com.blog.application.exception;

/**
 * The class is a specific exception class for exception that occur throughout
 * this application.
 */
public class BlogException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new blog exception.
	 *
	 * @param exception the exception
	 */
	public BlogException(String exception) {
		super(exception);
	}
}