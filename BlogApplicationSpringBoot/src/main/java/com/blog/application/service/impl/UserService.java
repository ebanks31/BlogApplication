package com.blog.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.model.User;
import com.blog.application.repositories.UserRepository;
import com.blog.application.service.IUserService;

/**
 * The Class UserService.
 */
@Service
public class UserService implements IUserService {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	/** The repository. */
	@Autowired
	private UserRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IUserService#findAll()
	 */
	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IUserService#findByUserId(long)
	 */
	@Override
	public User findByUserId(long userId) {
		Optional<User> value = repository.findById(userId);
		User user = null;

		if (value.isPresent()) {
			user = value.get();
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blog.application.service.IUserService#addUser(com.blog.application.model.
	 * User)
	 */
	@Override
	public void addUser(User user) {
		repository.save(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IUserService#deleteUser(long)
	 */
	@Override
	public void deleteUser(long userId) {
		repository.deleteById(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blog.application.service.IUserService#editUser(long)
	 */
	@Override
	public void editUser(long userId, User user) {
		if (userId != 0) {
			Optional<User> userOptional = repository.findById(userId);
			User retrievedUser = null;

			if (userOptional.isPresent()) {
				retrievedUser = userOptional.get();
			}

			if (retrievedUser != null) {
				retrievedUser.setFirstname(user.getFirstname());
				retrievedUser.setLastname(user.getLastname());
				retrievedUser.setMiddlename(user.getMiddlename());

				repository.save(retrievedUser);
			} else {
				LOGGER.info("No user was found");
			}
		}
	}
}
