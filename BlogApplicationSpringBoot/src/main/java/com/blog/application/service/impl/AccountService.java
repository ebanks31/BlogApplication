package com.blog.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.model.Account;
import com.blog.application.repositories.AccountRepository;
import com.blog.application.service.IAccountService;

/**
 * The Class AccountService.
 */
@Service
public class AccountService implements IAccountService {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

	/** The repository. */
	@Autowired
	private AccountRepository repository;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.blog.application.service.IAccountService#findAll()
	 */
	@Override
	public List<Account> findAll() {
		return repository.findAll();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.blog.application.service.IAccountService#findByAccountId(long)
	 */
	@Override
	public Account findByAccountId(long id) {
		Optional<Account> value = repository.findById(id);
		Account account = null;

		if (value.isPresent()) {
			account = value.get();
		}

		return account;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.blog.application.service.IAccountService#addAccount(com.blog.application.
	 * model.Account)
	 */
	@Override
	public void addAccount(Account account) {
		repository.save(account);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.blog.application.service.IAccountService#deleteAccount(long)
	 */
	@Override
	public void deleteAccount(long accountId) {
		repository.deleteById(accountId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.blog.application.service.IAccountService#editAccount(long)
	 */
	@Override
	public void editAccount(long accountId, Account account) {
		if (accountId != 0) {
			Optional<Account> accountOptional = repository.findById(accountId);
			Account retrievedAccount = null;

			if (accountOptional.isPresent()) {
				retrievedAccount = accountOptional.get();
			}

			if (retrievedAccount != null) {
				repository.save(retrievedAccount);
			} else {
				LOGGER.info("No account was found");
			}
		}
	}
}
