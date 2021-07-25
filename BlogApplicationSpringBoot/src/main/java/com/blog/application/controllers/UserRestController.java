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
import com.blog.application.model.User;
import com.blog.application.service.IUserService;
import com.blog.application.validator.UserValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class UserRestController.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserRestController {
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	private static final String THE_USER_IS_INVALID = "The user is invalid";

	/** The user service. */
	@Autowired
	IUserService userService;

	@Autowired
	UserValidator userValidator;

	/**
	 * Gets the users.
	 *
	 * @return the users
	 * @throws BlogException
	 */
	@GetMapping("/users")
	@ApiOperation(value = "View a list of users", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of users"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<List<User>> getUsers() throws BlogException {
		List<User> users = userService.findAll();
		LOGGER.info("users12345 : {}", users);
		boolean valid = userValidator.validateUserList(users);

		if (valid) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			throw new BlogException(THE_USER_IS_INVALID);
		}
	}

	/**
	 * Gets the user by id.
	 *
	 * @param userId the user id
	 * @return the user by id
	 * @throws BlogException
	 */
	@GetMapping("/users/user/{userId}")
	@ApiOperation(value = "Retreives a user by Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) throws BlogException {
		User user = userService.findByUserId(userId);
		boolean valid = userValidator.validateUser(user);

		if (valid) {
			LOGGER.info("user: {}", user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			throw new BlogException(THE_USER_IS_INVALID);
		}
	}

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @return the response entity
	 * @throws BlogException
	 */
	@PostMapping(value = "/users/user/add", produces = "application/json")
	@ApiOperation(value = "Adds an user", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully adds an user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> addUser(@RequestBody User user) throws BlogException {
		boolean valid = userValidator.validateUser(user);

		if (valid) {
			LOGGER.info("user: {}", user);
			userService.addUser(user);
			return new ResponseEntity<>("User has been added successfully", HttpStatus.OK);
		} else {
			throw new BlogException(THE_USER_IS_INVALID);
		}
	}

	/**
	 * Edits the user.
	 *
	 * @param userId the user id
	 * @return the response entity
	 * @throws BlogException
	 */
	@PutMapping(value = "/users/user/edit/{userId}", produces = "application/json")
	@ApiOperation(value = "Edits an user", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edits an user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> editUser(@PathVariable("userId") long userId, @RequestBody User user)
			throws BlogException {
		boolean validUserId = userValidator.validateNumber(userId);
		boolean validUser = userValidator.validateUser(user);

		if (validUserId && validUser) {
			LOGGER.info("userId: {}", userId);
			userService.editUser(userId, user);
			return new ResponseEntity<>("User has been edited successfully", HttpStatus.OK);
		} else {
			throw new BlogException(THE_USER_IS_INVALID);
		}
	}

	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 * @return the response entity
	 * @throws BlogException
	 */
	@DeleteMapping(value = "/users/user/delete/{userId}", produces = "application/json")
	@ApiOperation(value = "Deletes an user", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deletes an user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> deleteUser(@PathVariable("userId") long userId) throws BlogException {
		boolean valid = userValidator.validateNumber(userId);
		if (valid) {
			LOGGER.info("userId: {}", userId);
			userService.deleteUser(userId);
			return new ResponseEntity<>("User has been edited successfully", HttpStatus.OK);
		} else {
			throw new BlogException(THE_USER_IS_INVALID);
		}
	}
}