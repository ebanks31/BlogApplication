package com.blog.application.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.blog.application.model.User;
import com.blog.application.service.impl.ServiceOperations;

public class UserValidatorTest extends ServiceOperations {

	@Autowired
	UserValidator validator;

	@Test
	public void testValidateUserSuccess() throws Exception {
		boolean valid = validator.validateUser(mockUser());
		assertTrue(valid);
	}

	@Test
	public void testValidateUserNullFailure() throws Exception {
		boolean valid = validator.validateUser(null);
		assertTrue(valid);
	}

	@Test
	public void testValidateUserInvalidUserIdFailure() throws Exception {
		User user = mockUser();
		user.setUserId(0L);
		boolean valid = validator.validateUser(user);
		assertFalse(valid);
	}

	@Test
	public void testValidateUserInvalidUserNullFailure() throws Exception {
		boolean valid = validator.validateUser(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateUserListSuccess() throws Exception {
		boolean valid = validator.validateUserList(mockUserList());
		assertTrue(valid);
	}

	@Test
	public void testValidateUserListNullFailure() throws Exception {
		boolean valid = validator.validateUserList(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateUserListEmptyFailure() throws Exception {
		boolean valid = validator.validateUserList(Collections.emptyList());
		assertFalse(valid);
	}

	@Test
	public void testValidateUserListInvalidUserIdFailure() throws Exception {
		User user = mockUser();
		user.setUserId(0L);
		boolean valid = validator.validateUserList(mockUserList(user));
		assertFalse(valid);
	}

}
