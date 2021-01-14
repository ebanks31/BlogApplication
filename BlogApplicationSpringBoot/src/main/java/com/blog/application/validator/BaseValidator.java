package com.blog.application.validator;

public interface BaseValidator {
	default boolean validateNumber(Long number) {
		return number > 0 ? Boolean.TRUE : Boolean.FALSE;
	}
}
