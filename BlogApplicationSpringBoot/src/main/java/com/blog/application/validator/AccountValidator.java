package com.blog.application.validator;

import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.blog.application.model.Account;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class AccountValidator extends BaseAccountValidator {

	@Override
	public boolean validateAccount(Account account) {
		boolean valid = false;

		if (account != null && ((account.getAccountId() != null && account.getAccountId() > 0L)
				&& (account.getUserId() != null && account.getUserId() > 0L) && account.getUser() == null
				&& StringUtils.isNotBlank(account.getUsername()))) {
			valid = true;
		}

		return valid;
	}

	@Override
	public boolean validateAccountList(List<Account> accountList) {
		boolean valid = false;

		if (!CollectionUtils.isEmpty(accountList)) {
			Predicate<Account> accountPredicate = account -> account != null
					&& (account.getAccountId() != null && account.getAccountId() > 0L)
					&& (account.getUserId() != null && account.getUserId() > 0L) && account.getUser() == null
					|| StringUtils.isNotBlank(account.getUsername());

			valid = accountList.stream().allMatch(accountPredicate);
		}

		return valid;
	}

}
