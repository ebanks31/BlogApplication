package com.blog.application.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.model.Account;
import com.blog.application.repositories.AccountRepository;
import com.blog.application.service.IAccountService;
import com.blog.application.service.impl.AccountService;

/**
 * The Class AccountController.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AccountServiceIntegrationTests {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(AccountServiceIntegrationTests.class);

	@TestConfiguration
	static class AccountServiceImplTestContextConfiguration {

		@Bean
		public IAccountService employeeService() {
			return new AccountService();
		}
	}

	/** The account service. */
	@Autowired
	private IAccountService accountService;

	private AccountRepository accountRepository = mock(AccountRepository.class);

	@Test
	public void getAccounts() {
		Account account = new Account();
		account.setAccountId(new Long(2));
		account.setEmail("test@gmail.com");
		account.setPassword("password");
		account.setUserId(new Long(1));

		when(accountRepository.findByAccountId(new Long(1))).thenReturn(account);
		Account accountFound = accountRepository.findByAccountId(new Long(1));

		assertThat(accountFound.getEmail()).isEqualTo(account.getEmail());
	}

	@Test
	public void getAccountsService() {
		Account account = new Account();
		account.setAccountId(new Long(2));
		account.setEmail("test@gmail.com");
		account.setPassword("password");
		account.setUserId(new Long(1));

		Account accountFound = accountService.findByAccountId(new Long(1));

		assertThat(accountFound.getEmail()).isEqualTo(account.getEmail());
	}

}