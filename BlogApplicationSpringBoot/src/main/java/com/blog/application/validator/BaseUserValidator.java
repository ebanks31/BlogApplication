package com.blog.application.validator;

import java.util.List;

import com.blog.application.model.User;

public abstract class BaseUserValidator extends BaseValidator {
	public abstract boolean validateUser(User user);

	public abstract boolean validateUserList(List<User> userList);
}
