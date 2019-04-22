package com.blog.application.service;

import java.util.List;

import com.blog.application.model.Account;

/**
 * The Interface IAccountService.
 */
public interface IAccountService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Account> findAll();

	/**
	 * Find by account id.
	 *
	 * @param id the id
	 * @return the account
	 */
	public Account findByAccountId(long id);

	/**
	 * Adds the account.
	 *
	 * @param account the account
	 */
	public void addAccount(Account account);

	/**
	 * Edits the account.
	 *
	 * @param accountId the account id
	 * @param account   the account
	 */
	public void editAccount(long accountId, Account account);

	/**
	 * Delete account.
	 *
	 * @param accountId the account id
	 */
	public void deleteAccount(long accountId);
}
