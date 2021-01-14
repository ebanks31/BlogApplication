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
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.blog.application.model.Account;
import com.google.gson.Gson;

public class AccountControllerUnitTests extends TestControllerOperations {

	private static final String ORIGIN = "origin";

	@Before()
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
	}

	@Test
	public void getAccountsTestOneAccount() throws Exception {
		List<Account> accounts = mockAccountList();

		when(accountService.findAll()).thenReturn(accounts);

		mockMvc.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].accountId", is(1))).andExpect(jsonPath("$[0].email", is("test@gmail.com")))
				.andExpect(jsonPath("$[0].password", is("password")));
	}

	@Test
	public void getAccountsTestMultipleAccount() throws Exception {
		Account firstAccount = mockAccount();
		Account secondAccount = mockAccount();
		secondAccount.setAccountId(new Long(2));

		List<Account> accounts = Arrays.asList(firstAccount, secondAccount);

		when(accountService.findAll()).thenReturn(accounts);

		mockMvc.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].accountId", is(1))).andExpect(jsonPath("$[0].email", is("test@gmail.com")))
				.andExpect(jsonPath("$[0].password", is("password"))).andExpect(jsonPath("$[1].accountId", is(2)))
				.andExpect(jsonPath("$[1].email", is("test1@gmail.com")))
				.andExpect(jsonPath("$[1].password", is("password1")));
	}

	@Test
	public void getAccountsTestNoAccount() throws Exception {
		List<Account> accounts = Arrays.asList();

		when(accountService.findAll()).thenReturn(accounts);

		ResultActions resultActions = mockMvc
				.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getAccountsTestAccountNull() throws Exception {
		List<Account> accounts = null;

		when(accountService.findAll()).thenReturn(accounts);

		ResultActions resultActions = mockMvc
				.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void addAccountTest() throws Exception {
		Account account = mockAccount();

		Gson gson = new Gson();
		String accountJson = gson.toJson(account);

		doNothing().when(accountService).addAccount(account);

		ResultActions resultActions = mockMvc
				.perform(post("/accounts/add").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
						.content(accountJson))
				.andExpect(content().string(containsString("Account was added"))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void deleteAccountTest() throws Exception {
		Account account = mockAccount();

		Gson gson = new Gson();
		String accountJson = gson.toJson(account);

		doNothing().when(accountService).deleteAccount(1);

		ResultActions resultActions = mockMvc
				.perform(delete("/accounts/delete/{accountId}", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void editAccountTest() throws Exception {
		Account account = mockAccount();

		Gson gson = new Gson();
		String accountJson = gson.toJson(account);

		doNothing().when(accountService).editAccount(1, account);

		ResultActions resultActions = mockMvc
				.perform(put("/accounts/edit/{accountId}", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON).content(accountJson).content(accountJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getAccountById() throws Exception {
		Account account = mockAccount();

		Gson gson = new Gson();
		String accountJson = gson.toJson(account);

		when(accountService.findByAccountId(1)).thenReturn(account);

		mockMvc.perform(get("/accounts/{accountId}", 1).header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
				.content(accountJson)).andExpect(status().isOk()).andExpect(jsonPath("$.accountId", is(1)))
				.andExpect(jsonPath("$.email", is("test@gmail.com"))).andExpect(jsonPath("$.password", is("password")));
	}
}