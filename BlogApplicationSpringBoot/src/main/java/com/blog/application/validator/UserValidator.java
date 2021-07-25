package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.blog.application.model.User;

@Service
public class UserValidator extends BaseUserValidator {

	@Override
	public boolean validateUser(User user) {
		boolean valid = false;

		if (user != null && (user.getUserId() != null && user.getUserId() > 0L)) {
			valid = true;
		}

		return valid;
	}

	@Override
	public boolean validateUserList(List<User> userList) {
		boolean valid = false;

		if (!CollectionUtils.isEmpty(userList)) {
			Predicate<User> userPredicate = user -> user != null && (user.getUserId() != null && user.getUserId() > 0L);
			valid = userList.stream().allMatch(userPredicate);
		}

		return valid;
	}

}
