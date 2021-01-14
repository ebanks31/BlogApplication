package com.blog.application.validator;

import java.util.List;

import com.blog.application.model.Account;

public abstract class BaseAccountValidator implements BaseValidator {

	public abstract boolean validateAccountList(List<Account> bloglist);

	public abstract boolean validateAccount(Account account);
}
