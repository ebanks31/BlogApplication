package com.blog.application.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.blog.application.service.impl.ServiceOperations;

public class BaseValidatorTest extends ServiceOperations {
	@Autowired
	BaseValidator validator;

	@Test
	public void testNumberTrue() {
		assertTrue(validator.validateNumber(1L));
	}

	@Test
	public void testNumberFalse() {
		assertTrue(validator.validateNumber(0L));
	}
}
