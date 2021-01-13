package com.blog.application.validator;

public abstract class BaseValidator {
	public boolean validateNumber(Long number) {
		return number > 0 ? true : false;
	}
}
