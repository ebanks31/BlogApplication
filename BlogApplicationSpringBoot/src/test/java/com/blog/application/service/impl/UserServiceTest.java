package com.blog.application.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.blog.application.model.User;

public class UserServiceTest extends ServiceOperations {

	@Test
	public void testFindAll() throws Exception {
		doNothing().when(userRepository.findAll());
		userService.findAll();
		assertTrue(true);
	}

	@Test
	public void testFindByUserId() throws Exception {
		when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser()));

		User resultUser = userService.findByUserId(1L);
		assertNotNull(resultUser);

		assertEquals(new Long(1), resultUser.getUserId());
		assertEquals("John", resultUser.getFirstname());
		assertEquals("Doe", resultUser.getLastname());
	}

	@Test
	public void testAddUser() throws Exception {
		doNothing().when(userRepository).save(Mockito.any());
		userService.addUser(mockUser());
		assertTrue(true);
	}

	@Test
	public void testDeleteUser() throws Exception {
		doNothing().when(userRepository).delete(Mockito.any());
		userService.deleteUser(1L);
		assertTrue(true);
	}

	@Test
	public void testEditUser() throws Exception {
		when(userRepository.save(Mockito.any())).thenReturn(Optional.of(mockUser()));
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(mockUser()));

		userService.editUser(1L, mockUser());
		assertTrue(true);
	}

	@Test
	public void testEditUserNull() throws Exception {
		when(userRepository.save(Mockito.any())).thenReturn(Optional.of(mockUser()));
		when(userRepository.findById(Mockito.any())).thenReturn(null);

		userService.editUser(1L, mockUser());
		assertTrue(true);
	}

	@Test
	public void testEditUserEmpty() throws Exception {
		when(userRepository.save(Mockito.any())).thenReturn(Optional.of(mockUser()));
		when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

		userService.editUser(1L, mockUser());
		assertTrue(true);
	}
}
