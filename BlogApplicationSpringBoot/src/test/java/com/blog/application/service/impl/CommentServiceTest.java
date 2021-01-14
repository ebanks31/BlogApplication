package com.blog.application.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
		when(commentRepository.findById(1L)).thenReturn(Optional.of(mockComment()));

		Comment resultcomment = commentService.findByCommentId(1L);

		assertNotNull(resultcomment);
		assertEquals(new Long(1), resultcomment.getCommentId());
		assertEquals("Comment", resultcomment.getComment());
		assertEquals("TestStatus", resultcomment.getStatus());
	}

	@Test
	public void testFindByCommentByBlogIdAndBlogPostId() throws Exception {
		when(commentRepository.findCommentByBlogIdAndBlogPostId(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(Optional.of(mockComment()));

		Comment resultcomment = commentService.findByCommentByBlogIdAndBlogPostId(1L, 1L, 1L);

		assertNotNull(resultcomment);
		assertEquals(new Long(1), resultcomment.getCommentId());
		assertEquals("Comment", resultcomment.getComment());
		assertEquals("TestStatus", resultcomment.getStatus());
	}

	@Test
	public void testFindByCommentByBlogIdAndBlogPostIdCommentNull() throws Exception {
		when(commentRepository.findCommentByBlogIdAndBlogPostId(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(null);

		Comment resultcomment = commentService.findByCommentByBlogIdAndBlogPostId(1L, 1L, 1L);

		assertNull(resultcomment);
	}

	@Test
	public void testFindByCommentByBlogIdAndBlogPostIdCommentEmpty() throws Exception {
		when(commentRepository.findCommentByBlogIdAndBlogPostId(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(Optional.empty());

		Comment resultcomment = commentService.findByCommentByBlogIdAndBlogPostId(1L, 1L, 1L);

		assertNull(resultcomment);
	}

	@Test
	public void testAddComment() throws Exception {
		doNothing().when(commentRepository).save(Mockito.any());
		commentService.addComment(mockComment());
		assertTrue(true);
	}

	@Test
	public void testAddCommentWithBlogIdAndBlogPostId() throws Exception {
		doNothing().when(commentRepository).save(Mockito.any());
		commentService.addCommentWithBlogIdAndBlogPostId(mockComment(), 1L, 1L);
		assertTrue(true);
	}

	@Test
	public void testDeleteComment() throws Exception {
		doNothing().when(commentRepository).delete(Mockito.any());
		commentService.deleteComment(1L);
		assertTrue(true);
	}

	@Test
	public void testDeleteCommentWithBlogIdAndBlogPostId() throws Exception {
		doNothing().when(commentRepository).delete(Mockito.any());
		commentService.deleteCommentWithBlogIdAndBlogPostId(1L, 1L, 1L);
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

	@Test
	public void testEditCommentByBlogIdAndBlogPostId() throws Exception {
		when(commentRepository.findCommentByBlogIdAndBlogPostId(1L, 1L, 1L)).thenReturn(Optional.of(mockComment()));

		Comment comment = mockComment();
		commentService.editComment(comment.getCommentId(), comment);

		assertTrue(true);
	}

	@Test
	public void testEditCommentByBlogIdAndBlogPostIdCommentValueNull() throws Exception {
		when(commentRepository.findCommentByBlogIdAndBlogPostId(1L, 1L, 1L)).thenReturn(Optional.of(mockComment()));

		Comment comment = mockComment();
		comment.setComment(null);
		commentService.editComment(comment.getCommentId(), comment);

		assertFalse(true);
	}

	@Test
	public void testEditCommentByBlogIdAndBlogPostIdCommentNull() throws Exception {
		when(commentRepository.findCommentByBlogIdAndBlogPostId(1L, 1L, 1L)).thenReturn(null);

		Comment comment = mockComment();
		commentService.editComment(comment.getCommentId(), comment);

		assertFalse(true);
	}

	@Test
	public void testEditCommentByBlogIdAndBlogPostIdCommentEmpty() throws Exception {
		when(commentRepository.findCommentByBlogIdAndBlogPostId(1L, 1L, 1L)).thenReturn(Optional.empty());

		Comment comment = mockComment();
		commentService.editComment(comment.getCommentId(), comment);

		assertFalse(true);
	}
}
