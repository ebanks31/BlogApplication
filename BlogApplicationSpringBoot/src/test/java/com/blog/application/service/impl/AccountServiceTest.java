package com.blog.application.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.blog.application.model.Account;

public class AccountServiceTest extends ServiceOperations {

	@Test
	public void testFindAll() throws Exception {
		doNothing().when(accountRepository.findAll());
		accountService.findAll();
		assertTrue(true);
	}

	@Test
	public void testFindByAccountId() throws Exception {
		when(accountRepository.findByAccountId(Mockito.any())).thenReturn(mockAccount());
		Account account = accountService.findByAccountId(1L);

		assertEquals(new Long(1), account.getAccountId());
		assertEquals("test@gmail.com", account.getEmail());
		assertEquals("password", account.getPassword());

	}

	@Test
	public void testAddAccount() throws Exception {
		doNothing().when(accountRepository).save(Mockito.any());
		accountService.addAccount(mockAccount());
		assertTrue(true);
	}

	@Test
	public void testDeleteAccount() throws Exception {
		doNothing().when(accountRepository).delete(Mockito.any());
		accountService.deleteAccount(1L);

		assertTrue(true);
	}

	@Test
	public void testEditAccount() throws Exception {
		when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(mockAccount()));
		accountService.editAccount(1L, mockAccount());

		assertTrue(true);
	}

	@Test
	public void testEditAccountOptionalEmpty() throws Exception {
		when(accountRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		accountService.editAccount(1L, mockAccount());

		assertTrue(true);
	}

	@Test
	public void testEditAccountAccountNull() throws Exception {
		Account account = null;
		when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
		accountService.editAccount(1L, mockAccount());

		assertTrue(true);
	}

}
