package com.blog.application.controllers;

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

import com.blog.application.exception.BlogException;
import com.blog.application.model.Account;
import com.blog.application.service.IAccountService;
import com.blog.application.validator.AccountValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class sets up the controller for the Account REST end points
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class AccountRestController {

	private static final String ACCOUNT_VALIDATION_HAS_FAILED = "Account validation has failed";

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(AccountRestController.class);

	/** The account service. */
	@Autowired
	IAccountService accountService;

	@Autowired
	AccountValidator accountValidator;

	/**
	 * Gets the accounts.
	 *
	 * @return the accounts
	 * @throws BlogException
	 */
	@GetMapping(value = "/accounts", produces = "application/json")
	@ApiOperation(value = "View a list of accounts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<List<Account>> getAccounts() throws BlogException {
		LOGGER.info("getAccounts()");
		List<Account> accounts = accountService.findAll();

		boolean valid = accountValidator.validateAccountList(accounts);

		if (valid) {
			return new ResponseEntity<>(accounts, HttpStatus.OK);
		} else {
			throw new BlogException(ACCOUNT_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Gets the account by id.
	 *
	 * @param accountId the account id
	 * @return the account by id
	 * @throws BlogException
	 */
	@GetMapping("/accounts/{accountId}")
	@ApiOperation(value = "Gets the account by account Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved account"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<Account> getAccountById(@PathVariable("accountId") long accountId) throws BlogException {
		LOGGER.info("getAccountById()");
		Account account = accountService.findByAccountId(accountId);

		boolean valid = accountValidator.validateAccount(account);
		LOGGER.info("Accounts: {}", account);

		if (valid) {
			return new ResponseEntity<>(account, HttpStatus.OK);
		} else {
			throw new BlogException(ACCOUNT_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Adds the accounts.
	 *
	 * @param account the account
	 * @return the response entity
	 * @throws BlogException
	 */
	@PostMapping("/accounts/add")
	@ApiOperation(value = "Add an account", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the account"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> addAccount(@RequestBody Account account) throws BlogException {
		LOGGER.info("addAccounts()");
		boolean valid = accountValidator.validateAccount(account);

		if (valid) {
			accountService.addAccount(account);
			return new ResponseEntity<>("Account was added", HttpStatus.OK);
		} else {
			throw new BlogException(ACCOUNT_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Edits the accounts.
	 *
	 * @param account the account
	 * @return the response entity
	 * @throws BlogException
	 */
	@PutMapping("/accounts/edit/{accountId}")
	@ApiOperation(value = "Edits the account", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited the account"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> editAccount(@PathVariable("accountId") long accountId, @RequestBody Account account)
			throws BlogException {
		LOGGER.info("editAccount()");
		boolean validAccount = accountValidator.validateAccount(account);
		boolean validAccountId = accountValidator.validateNumber(accountId);

		if (validAccount && validAccountId) {
			accountService.editAccount(accountId, account);
			return new ResponseEntity<>("Account was edited", HttpStatus.OK);
		} else {
			throw new BlogException(ACCOUNT_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Deletes the accounts.
	 *
	 * @param account the account
	 * @return the response entity
	 * @throws BlogException
	 */
	@DeleteMapping("/accounts/delete/{accountId}")
	@ApiOperation(value = "Deletes the account", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deletes the account"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> deleteAccount(@PathVariable("accountId") long accountId) throws BlogException {
		LOGGER.info("deleteAccount()");
		accountService.deleteAccount(accountId);
		boolean validAccountId = accountValidator.validateNumber(accountId);

		if (validAccountId) {
			return new ResponseEntity<>("Account was deleted", HttpStatus.OK);
		} else {
			throw new BlogException(ACCOUNT_VALIDATION_HAS_FAILED);
		}
	}
}