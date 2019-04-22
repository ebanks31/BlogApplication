package com.blog.application.service;

import java.util.List;

import com.blog.application.model.User;

/**
 * The Interface IUserService.
 */
public interface IUserService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<User> findAll();

	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User findByUserId(long userId);

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	public void addUser(User user);

	/**
	 * Edits the user.
	 *
	 * @param userId the user id
	 * @param user   the user
	 */
	public void editUser(long userId, User user);

	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 */
	public void deleteUser(long userId);
}
