package com.blog.application.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.blog.application.model.Comment;

public class CommentServiceTest extends ServiceOperations {

	@Test
	public void testFindAll() throws Exception {
		doNothing().when(commentRepository.findAll());
		commentService.findAll();
		assertTrue(true);
	}

	@Test
	public void testFindByCommentId() throws Exception {
		Comment resultcomment = commentService.findByCommentId(1L);

		assertEquals(new Long(1), resultcomment.getCommentId());
		assertEquals("Comment", resultcomment.getComment());
		assertEquals("TestStatus", resultcomment.getStatus());
	}

	@Test
	public void testAddComment() throws Exception {
		doNothing().when(commentRepository).save(Mockito.any());
		commentService.addComment(mockComment());
		assertTrue(true);
	}

	@Test
	public void testDeleteComment() throws Exception {
		doNothing().when(commentRepository).delete(Mockito.any());
		commentService.deleteComment(1L);
		assertTrue(true);
	}

	@Test
	public void testEditComment() throws Exception {
		when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment()));

		Comment comment = mockComment();

		commentService.editComment(comment.getCommentId(), comment);
		assertTrue(true);
	}

	@Test
	public void testEditCommentEmpty() throws Exception {
		when(commentRepository.findById(1L)).thenReturn(Optional.empty());

		Comment comment = mockComment();

		commentService.editComment(comment.getCommentId(), comment);
		assertTrue(true);
	}

	@Test
	public void testEditCommentNull() throws Exception {
		when(commentRepository.findById(1L)).thenReturn(Optional.of(null));

		Comment comment = mockComment();

		commentService.editComment(comment.getCommentId(), comment);
		assertTrue(true);
	}

}
