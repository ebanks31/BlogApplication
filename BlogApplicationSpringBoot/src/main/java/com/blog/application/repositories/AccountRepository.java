package com.blog.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.blog.application.model.Account;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("from Account")
	public List<Account> findAllAccounts();

	public Account findByAccountId(Long id);
}
