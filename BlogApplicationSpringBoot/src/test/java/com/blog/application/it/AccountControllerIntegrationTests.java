package com.blog.application.it;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.blog.application.BlogApplication;
import com.blog.application.model.Account;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BlogApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AccountControllerIntegrationTests {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(AccountControllerIntegrationTests.class);
	private static final String ORIGIN = "origin";

	@Autowired
	private MockMvc mvc;

	@Test
	public void getAccountsOneAccount() throws Exception {
		Account account = new Account();
		account.setAccountId(new Long(2));
		account.setEmail("test@gmail.com");
		account.setPassword("password");
		account.setUserId(new Long(1));

		Gson gson = new Gson();
		String accountJson = gson.toJson(account);

		mvc.perform(
				post("/accounts/add").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andExpect(content().string(containsString("Account was added"))).andExpect(status().isOk());

		mvc.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].accountId", is(1))).andExpect(jsonPath("$[0].email", is("test@gmail.com")))
				.andExpect(jsonPath("$[0].password", is("password")));
	}

	@Test
	public void getAccountsMultipleAccounts() throws Exception {

		Account account = new Account();
		account.setAccountId(new Long(2));
		account.setEmail("test@gmail.com");
		account.setPassword("password");
		account.setUserId(new Long(1));

		mvc.perform(get("/accounts").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].accountId", is(1))).andExpect(jsonPath("$[0].email", is("test@gmail.com")))
				.andExpect(jsonPath("$[0].password", is("password"))).andExpect(jsonPath("$[1].accountId", is(2)))
				.andExpect(jsonPath("$[1].email", is("test1@gmail.com")))
				.andExpect(jsonPath("$[1].password", is("password1")));
	}
}