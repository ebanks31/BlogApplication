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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.blog.application.model.User;
import com.google.gson.Gson;

public class UserControllerUnitTests extends TestOperations {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(UserControllerUnitTests.class);

	@Test
	public void getUsersOneUser() throws Exception {
		User user = mockUser();

		List<User> users = Arrays.asList(user);

		when(userService.findAll()).thenReturn(users);

		mockMvc.perform(get("/users").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].userId", is(1))).andExpect(jsonPath("$[0].firstname", is("John")))
				.andExpect(jsonPath("$[0].lastname", is("Doe")));
	}

	/**
	 * Gets the users test multiple users.
	 *
	 * @return the users test multiple users
	 * @throws Exception the exception
	 */
	@Test
	public void getUsersTestMultipleUsers() throws Exception {
		User firstUser = mockUser();

		User secondUser = new User();
		secondUser.setUserId(new Long(2));
		secondUser.setFirstname("John1");
		secondUser.setLastname("Doe1");

		List<User> users = Arrays.asList(firstUser, secondUser);

		when(userService.findAll()).thenReturn(users);

		mockMvc.perform(get("/users").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].userId", is(1))).andExpect(jsonPath("$[0].firstname", is("John")))
				.andExpect(jsonPath("$[0].lastname", is("Doe"))).andExpect(jsonPath("$[1].userId", is(2)))
				.andExpect(jsonPath("$[1].firstname", is("John1"))).andExpect(jsonPath("$[1].lastname", is("Doe1")));
	}

	/**
	 * Gets the users test no user.
	 *
	 * @return the users test no user
	 * @throws Exception the exception
	 */
	@Test
	public void getUsersTestNoUser() throws Exception {
		User user = mockUser();

		List<User> users = Arrays.asList();

		when(userService.findAll()).thenReturn(users);

		ResultActions resultActions = mockMvc
				.perform(get("/users").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Gets the users test user null.
	 *
	 * @return the users test user null
	 * @throws Exception the exception
	 */
	@Test
	public void getUsersTestUserNull() throws Exception {
		User user = mockUser();

		List<User> users = null;

		when(userService.findAll()).thenReturn(users);

		ResultActions resultActions = mockMvc
				.perform(get("/users").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Adds the user test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void addUserTest() throws Exception {
		User user = mockUser();

		Gson gson = new Gson();
		String userJson = gson.toJson(user);

		doNothing().when(userService).addUser(user);

		ResultActions resultActions = mockMvc
				.perform(post("/users/user/add").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
						.content(userJson))
				.andExpect(content().string(containsString("User has been added successfully")))
				.andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Delete user test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteUserTest() throws Exception {
		User user = mockUser();

		Gson gson = new Gson();
		String userJson = gson.toJson(user);

		doNothing().when(userService).deleteUser(1);

		ResultActions resultActions = mockMvc
				.perform(delete("/users/user/delete/{userId}", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON).content(userJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Edits the user test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void editUserTest() throws Exception {
		User user = mockUser();

		Gson gson = new Gson();
		String userJson = gson.toJson(user);

		doNothing().when(userService).editUser(1, user);

		ResultActions resultActions = mockMvc.perform(put("/users/user/edit/{userId}", 1).header(ORIGIN, "*")
				.contentType(MediaType.APPLICATION_JSON).content(userJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Gets the user by id.
	 *
	 * @return the user by id
	 * @throws Exception the exception
	 */
	@Test
	public void getUserById() throws Exception {
		User user = mockUser();

		Gson gson = new Gson();
		String accountJson = gson.toJson(user);

		when(userService.findByUserId(1)).thenReturn(user);

		mockMvc.perform(get("/users/user/{userId}", 1).header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
				.content(accountJson)).andExpect(status().isOk()).andExpect(jsonPath("$.userId", is(1)))
				.andExpect(jsonPath("$.firstname", is("John"))).andExpect(jsonPath("$.lastname", is("Doe")));
	}
}