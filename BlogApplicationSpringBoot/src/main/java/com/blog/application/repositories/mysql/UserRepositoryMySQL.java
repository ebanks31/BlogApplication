package com.blog.application.repositories.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.application.model.User;

/**
 * The Interface UserRepositoryMySQL.
 */
@Repository("userRepositoryMySQL")
public interface UserRepositoryMySQL extends JpaRepository<User, Long> {
}