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

import com.blog.application.model.User;
import com.blog.application.service.IUserService;

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

	/** The user service. */
	@Autowired
	IUserService userService;

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	@GetMapping("/users")
	@ApiOperation(value = "View a list of users", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of users"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userService.findAll();
		LOGGER.info("users12345 : {}", users);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * Gets the user by id.
	 *
	 * @param userId the user id
	 * @return the user by id
	 */
	@GetMapping("/users/user/{userId}")
	@ApiOperation(value = "Retreives a user by Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
		User user = userService.findByUserId(userId);
		LOGGER.info("user: {}", user);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @return the response entity
	 */
	@PostMapping(value = "/users/user/add", produces = "application/json")
	@ApiOperation(value = "Adds an user", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully adds an user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> addUser(@RequestBody User user) {
		LOGGER.info("user: {}", user);
		userService.addUser(user);

		return new ResponseEntity<>("User has been added successfully", HttpStatus.OK);
	}

	/**
	 * Edits the user.
	 *
	 * @param userId the user id
	 * @return the response entity
	 */
	@PutMapping(value = "/users/user/edit/{userId}", produces = "application/json")
	@ApiOperation(value = "Edits an user", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edits an user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> editUser(@PathVariable("userId") long userId, @RequestBody User user) {
		LOGGER.info("userId: {}", userId);
		userService.editUser(userId, user);

		return new ResponseEntity<>("User has been edited successfully", HttpStatus.OK);
	}

	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/users/user/delete/{userId}", produces = "application/json")
	@ApiOperation(value = "Deletes an user", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deletes an user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> deleteUser(@PathVariable("userId") long userId) {
		LOGGER.info("userId: {}", userId);
		userService.deleteUser(userId);

		return new ResponseEntity<>("User has been edited successfully", HttpStatus.OK);
	}
}