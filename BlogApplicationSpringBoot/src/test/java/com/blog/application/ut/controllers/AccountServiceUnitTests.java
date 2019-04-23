package com.blog.application.ut.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.model.Account;
import com.blog.application.service.IAccountService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class AccountController.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AccountServiceUnitTests {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(AccountServiceUnitTests.class);

	/** The account service. */
	@Autowired
	IAccountService accountService;

	/**
	 * Gets the accounts.
	 *
	 * @return the accounts
	 */
	@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
	@GetMapping(value = "/accounts", produces = "application/json")
	@ApiOperation(value = "View a list of accounts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<Account>> getAccounts() {
		LOGGER.info("getAccounts()");
		List<Account> accounts = accountService.findAll();

		LOGGER.info("Accounts: {}", accounts.toString());
		accounts.forEach(account -> {
			System.out.println(account.getAccountId());
			System.out.println(account.getStatus());
		});
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}

	/**
	 * Gets the account by id.
	 *
	 * @param accountId the account id
	 * @return the account by id
	 */
	@GetMapping("/accounts/{accountId}")
	@ApiOperation(value = "View a list of accounts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<Account> getAccountById(@PathVariable("accountId") long accountId) {
		LOGGER.info("getAccountById()");
		Account account = accountService.findByAccountId(accountId);

		LOGGER.info("Accounts: {}", account);
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	/**
	 * Adds the accounts.
	 *
	 * @param account the account
	 * @return the response entity
	 */
	@PostMapping("/accounts/add")
	@ApiOperation(value = "Add an account", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the account"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> addAccounts(@RequestBody Account account) {
		LOGGER.info("getAccounts()");
		accountService.addAccount(account);
		;

		return new ResponseEntity<>("Account was added", HttpStatus.OK);
	}

	/**
	 * Edits the accounts.
	 *
	 * @param account the account
	 * @return the response entity
	 */
	@PutMapping("/accounts/add/{accountId}")
	@ApiOperation(value = "Edits the account", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited the account"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> editAccount(@PathVariable("accountId") long accountId, @RequestBody Account account) {
		LOGGER.info("getAccounts()");
		accountService.editAccount(accountId, account);

		return new ResponseEntity<>("Account was edited", HttpStatus.OK);
	}

	/**
	 * Deletes the accounts.
	 *
	 * @param account the account
	 * @return the response entity
	 */
	@DeleteMapping("/accounts/delete/{accountId}")
	@ApiOperation(value = "Deletes the account", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deletes the account"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> deletedAccount(@PathVariable("accountId") long accountId) {
		LOGGER.info("getAccounts()");
		accountService.deleteAccount(accountId);

		return new ResponseEntity<>("Account was deleted", HttpStatus.OK);
	}
}