package com.blog.application.ut.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.blog.application.controllers.AccountRestController;
import com.blog.application.model.Account;
import com.blog.application.service.IAccountService;
import com.google.gson.Gson;

/**
 * The Class AccountController.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AccountRestController.class)
public class AccountControllerUnitTests {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(AccountControllerUnitTests.class);
	private static final String ORIGIN = "origin";

	@Autowired
	private MockMvc mvc;

	/** The account service. */
	@MockBean
	IAccountService accountService;

	@Before()
	public void setUp() {

	}

	@Test
	public void getAccountsTestOneAccount() throws Exception {
		LOGGER.info("getAccountsTestOneAccount()");

		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");

		List<Account> accounts = Arrays.asList(account);

		when(accountService.findAll()).thenReturn(accounts);

		mvc.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].accountId", is(1))).andExpect(jsonPath("$[0].email", is("test@gmail.com")))
				.andExpect(jsonPath("$[0].password", is("password")));
	}

	@Test
	public void getAccountsTestMultipleAccount() throws Exception {
		LOGGER.info("getAccountsTestMultipleAccount()");

		Account firstAccount = new Account();
		firstAccount.setAccountId(new Long(1));
		firstAccount.setEmail("test@gmail.com");
		firstAccount.setPassword("password");

		Account secondAccount = new Account();
		secondAccount.setAccountId(new Long(2));
		secondAccount.setEmail("test1@gmail.com");
		secondAccount.setPassword("password1");

		List<Account> accounts = Arrays.asList(firstAccount, secondAccount);

		when(accountService.findAll()).thenReturn(accounts);

		mvc.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].accountId", is(1))).andExpect(jsonPath("$[0].email", is("test@gmail.com")))
				.andExpect(jsonPath("$[0].password", is("password"))).andExpect(jsonPath("$[1].accountId", is(2)))
				.andExpect(jsonPath("$[1].email", is("test1@gmail.com")))
				.andExpect(jsonPath("$[1].password", is("password1")));
	}

	@Test
	public void getAccountsTestNoAccount() throws Exception {
		LOGGER.info("getAccountsTestNoAccount()");

		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");

		List<Account> accounts = Arrays.asList();

		when(accountService.findAll()).thenReturn(accounts);

		ResultActions resultActions = mvc
				.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getAccountsTestAccountNull() throws Exception {
		LOGGER.info("getAccountsTestAccountNull()");

		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");

		List<Account> accounts = null;

		when(accountService.findAll()).thenReturn(accounts);

		ResultActions resultActions = mvc
				.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void addAccountTest() throws Exception {
		LOGGER.info("addAccountsTest()");

		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");

		Gson gson = new Gson();
		String accountJson = gson.toJson(account);

		doNothing().when(accountService).addAccount(account);

		ResultActions resultActions = mvc
				.perform(post("/accounts/add").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
						.content(accountJson))
				.andExpect(content().string(containsString("Account was added"))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void deleteAccountTest() throws Exception {
		LOGGER.info("deleteAccountTest()");

		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");

		Gson gson = new Gson();
		String accountJson = gson.toJson(account);

		doNothing().when(accountService).deleteAccount(1);

		ResultActions resultActions = mvc
				.perform(delete("/accounts/delete/{accountId}", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void editAccountTest() throws Exception {
		LOGGER.info("editAccountTest()");

		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");

		Gson gson = new Gson();
		String accountJson = gson.toJson(account);

		doNothing().when(accountService).editAccount(1, account);

		ResultActions resultActions = mvc
				.perform(put("/accounts/edit/{accountId}", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON).content(accountJson).content(accountJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getAccountById() throws Exception {
		LOGGER.info("getAccountById()");

		Account account = new Account();
		account.setAccountId(new Long(1));
		account.setEmail("test@gmail.com");
		account.setPassword("password");

		Gson gson = new Gson();
		String accountJson = gson.toJson(account);

		when(accountService.findByAccountId(1)).thenReturn(account);

		mvc.perform(get("/accounts/{accountId}", 1).header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
				.content(accountJson)).andExpect(status().isOk()).andExpect(jsonPath("$.accountId", is(1)))
				.andExpect(jsonPath("$.email", is("test@gmail.com"))).andExpect(jsonPath("$.password", is("password")));
	}
}