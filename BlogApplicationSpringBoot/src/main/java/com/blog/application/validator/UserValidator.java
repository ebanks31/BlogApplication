package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;

import com.blog.application.model.User;

public class UserValidator extends BaseUserValidator {

	@Override
	public boolean validateUser(User user) {
		boolean valid = true;

		if (user != null && (user.getUserId() > 0)) {
			valid = false;
		}

		return valid;
	}

	@Override
	public boolean validateUserList(List<User> userList) {
		boolean valid = true;

		if (!CollectionUtils.isEmpty(userList)) {
			Predicate<User> userPredicate = user -> user != null && user.getUserId() > 0;
			valid = userList.stream().noneMatch(userPredicate);
		} else {
			valid = false;
		}

		return valid;
	}

}
