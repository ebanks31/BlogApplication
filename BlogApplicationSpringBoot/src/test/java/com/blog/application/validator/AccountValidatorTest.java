package com.blog.application.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.blog.application.model.Account;
import com.blog.application.service.impl.ServiceOperations;

public class AccountValidatorTest extends ServiceOperations {

	@InjectMocks
	AccountValidator validator;

	@Test
	public void testValidateAccountSuccess() throws Exception {
		boolean valid = validator.validateAccount(mockAccount());
		assertTrue(valid);
	}

	@Test
	public void testValidateAccountNullFailure() throws Exception {
		boolean valid = validator.validateAccount(null);
		assertTrue(valid);
	}

	@Test
	public void testValidateAccountInvalidAccountIdFailure() throws Exception {
		Account account = mockAccount();
		account.setAccountId(0L);
		boolean valid = validator.validateAccount(account);
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountInvalidUserIdFailure() throws Exception {
		Account account = mockAccount();
		account.setUserId(0L);
		boolean valid = validator.validateAccount(account);
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountUserNullFailure() throws Exception {
		Account account = mockAccount();
		account.setUser(null);
		boolean valid = validator.validateAccount(account);
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountUsernameEmptyFailure() throws Exception {
		Account account = mockAccount();
		account.setUsername(StringUtils.EMPTY);
		boolean valid = validator.validateAccount(account);
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountUsernameNullFailure() throws Exception {
		Account account = mockAccount();
		account.setUsername(null);
		boolean valid = validator.validateAccount(account);
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountListSuccess() throws Exception {
		boolean valid = validator.validateAccountList(mockAccountList());
		assertTrue(valid);
	}

	@Test
	public void testValidateAccountListNullFailure() throws Exception {
		boolean valid = validator.validateAccountList(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountListEmptyFailure() throws Exception {
		boolean valid = validator.validateAccountList(Collections.emptyList());
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountListInvalidUserIdFailure() throws Exception {
		Account account = mockAccount();
		account.setUserId(0L);
		boolean valid = validator.validateAccountList(mockAccountList(account));
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountListInvalidAccountIdFailure() throws Exception {
		Account account = mockAccount();
		account.setAccountId(0L);
		boolean valid = validator.validateAccountList(mockAccountList(account));
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountListInvalidUserNullFailure() throws Exception {
		Account account = mockAccount();
		account.setUser(null);
		boolean valid = validator.validateAccountList(mockAccountList(account));
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountListInvalidUsernameNullFailure() throws Exception {
		Account account = mockAccount();
		account.setUsername(null);
		boolean valid = validator.validateAccountList(mockAccountList(account));
		assertFalse(valid);
	}

	@Test
	public void testValidateAccountListInvalidUsernameEmptyFailure() throws Exception {
		Account account = mockAccount();
		account.setUsername(StringUtils.EMPTY);
		boolean valid = validator.validateAccountList(mockAccountList(account));
		assertFalse(valid);
	}
}
