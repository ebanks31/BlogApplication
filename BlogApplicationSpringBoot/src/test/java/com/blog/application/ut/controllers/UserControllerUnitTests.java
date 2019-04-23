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

import com.blog.application.controllers.UserRestController;
import com.blog.application.model.User;
import com.blog.application.service.IUserService;
import com.google.gson.Gson;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountController.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserControllerUnitTests {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(UserControllerUnitTests.class);

	/** The Constant ORIGIN. */
	private static final String ORIGIN = "origin";

	/** The mvc. */
	@Autowired
	private MockMvc mvc;

	/** The user service. */
	@MockBean
	IUserService userService;

	/**
	 * Sets the up.
	 */
	@Before()
	public void setUp() {

	}

	@Test
	public void getUsersOneUser() throws Exception {
		LOGGER.info("getUsersOneUser()");

		User user = new User();
		user.setUserId(new Long(1));
		user.setFirstname("John");
		user.setLastname("Doe");

		List<User> users = Arrays.asList(user);

		when(userService.findAll()).thenReturn(users);

		mvc.perform(get("/users").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
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
		LOGGER.info("getUsersTestMultipleAccount()");

		User firstUser = new User();
		firstUser.setUserId(new Long(1));
		firstUser.setFirstname("John");
		firstUser.setLastname("Doe");

		User secondUser = new User();
		secondUser.setUserId(new Long(2));
		secondUser.setFirstname("John1");
		secondUser.setLastname("Doe1");

		List<User> users = Arrays.asList(firstUser, secondUser);

		when(userService.findAll()).thenReturn(users);

		mvc.perform(get("/users").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
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
		LOGGER.info("getUsersTestNoAccount()");

		User user = new User();
		user.setUserId(new Long(1));
		user.setFirstname("John");
		user.setLastname("Doe");

		List<User> users = Arrays.asList();

		when(userService.findAll()).thenReturn(users);

		ResultActions resultActions = mvc
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
		LOGGER.info("getUsersTestAccountNull()");

		User user = new User();
		user.setUserId(new Long(1));
		user.setFirstname("John");
		user.setLastname("Doe");

		List<User> users = null;

		when(userService.findAll()).thenReturn(users);

		ResultActions resultActions = mvc
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
		LOGGER.info("addUserTest()");

		User user = new User();
		user.setUserId(new Long(1));
		user.setFirstname("John");
		user.setLastname("Doe");

		Gson gson = new Gson();
		String userJson = gson.toJson(user);

		doNothing().when(userService).addUser(user);

		ResultActions resultActions = mvc
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
		LOGGER.info("deleteUserTest()");

		User user = new User();
		user.setUserId(new Long(1));
		user.setFirstname("John");
		user.setLastname("Doe");

		Gson gson = new Gson();
		String userJson = gson.toJson(user);

		doNothing().when(userService).deleteUser(1);

		ResultActions resultActions = mvc
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
		LOGGER.info("editUserTest()");

		User user = new User();
		user.setUserId(new Long(1));
		user.setFirstname("John");
		user.setLastname("Doe");

		Gson gson = new Gson();
		String userJson = gson.toJson(user);

		doNothing().when(userService).editUser(1, user);

		ResultActions resultActions = mvc.perform(put("/users/user/edit/{userId}", 1).header(ORIGIN, "*")
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
		LOGGER.info("getUserById()");

		User user = new User();
		user.setUserId(new Long(1));
		user.setFirstname("John");
		user.setLastname("Doe");

		Gson gson = new Gson();
		String accountJson = gson.toJson(user);

		when(userService.findByUserId(1)).thenReturn(user);

		mvc.perform(get("/users/user/{userId}", 1).header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
				.content(accountJson)).andExpect(status().isOk()).andExpect(jsonPath("$.userId", is(1)))
				.andExpect(jsonPath("$.firstname", is("John"))).andExpect(jsonPath("$.lastname", is("Doe")));
	}
}