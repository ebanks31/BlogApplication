package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;

import com.blog.application.model.Account;

import io.micrometer.core.instrument.util.StringUtils;

public class AccountValidator extends BaseAccountValidator {

	@Override
	public boolean validateAccount(Account account) {
		boolean valid = true;

		if (account != null && (account.getAccountId() > 0 || account.getUserId() > 0 || account.getUser() == null
				|| StringUtils.isBlank(account.getUsername()))) {
			valid = false;
		}

		return valid;
	}

	@Override
	public boolean validateAccountList(List<Account> accountList) {
		boolean valid = true;

		if (!CollectionUtils.isEmpty(accountList)) {
			Predicate<Account> accountPredicate = account -> account != null
					&& (account.getAccountId() > 0 || account.getUserId() > 0 || account.getUser() == null
							|| StringUtils.isBlank(account.getUsername()));

			valid = accountList.stream().noneMatch(accountPredicate);
		} else {
			valid = false;
		}

		return valid;
	}

}
