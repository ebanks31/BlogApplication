package com.blog.application.it;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.blog.application.model.Account;
import com.blog.application.model.User;
import com.blog.application.repositories.AccountRepository;

/**
 * The Class AccountController.
 */
@RunWith(SpringRunner.class)
//@DataJpaTest
public class AccountRepositoryIntegrationTests {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(AccountRepositoryIntegrationTests.class);

	/** The account service. */
//	@Autowired
//	private TestEntityManager entityManager;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void testfindByAccountId() {
		LOGGER.info("testfindByAccountId()");

		// given
		User user = new User();

		Account account = new Account();
		account.setAccountId(new Long(2));
		account.setEmail("test@gmail.com");
		account.setPassword("password");
		account.setUserId(new Long(1));

//		entityManager.merge(user);
//		entityManager.merge(account);
//		entityManager.flush();

		// when
		Account accountFound = accountRepository.findByAccountId(new Long(2));
		LOGGER.info("accountFound: " + accountFound);

		// then
		assertThat(accountFound.getEmail()).isEqualTo(account.getEmail());
		assertThat(accountFound.getAccountId()).isEqualTo(account.getAccountId());
		assertThat(accountFound.getPassword()).isEqualTo(account.getPassword());

	}
}