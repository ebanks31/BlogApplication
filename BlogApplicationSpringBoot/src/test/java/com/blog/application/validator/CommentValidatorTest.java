package com.blog.application.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;
import org.mockito.InjectMocks;

import com.blog.application.model.Comment;
import com.blog.application.service.impl.ServiceOperations;

public class CommentValidatorTest extends ServiceOperations {

	@InjectMocks
	CommentValidator validator;

	@Test
	public void testValidateCommentSuccess() throws Exception {
		boolean valid = validator.validateComment(mockComment());
		assertTrue(valid);
	}

	@Test
	public void testValidateCommentNullFailure() throws Exception {
		boolean valid = validator.validateComment(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateUserInvalidCommentIdFailure() throws Exception {
		Comment comment = mockComment();
		comment.setCommentId(0L);
		boolean valid = validator.validateComment(comment);
		assertFalse(valid);
	}

	@Test
	public void testValidateUserInvalidBlogIdFailure() throws Exception {
		Comment comment = mockComment();
		comment.setBlogId(0L);
		boolean valid = validator.validateComment(comment);
		assertFalse(valid);
	}

	@Test
	public void testValidateUserInvalidBlogPostIdFailure() throws Exception {
		Comment comment = mockComment();
		comment.setBlogPostId(0L);
		boolean valid = validator.validateComment(comment);
		assertFalse(valid);
	}

	@Test
	public void testValidateCommentInvalidUserNullFailure() throws Exception {
		boolean valid = validator.validateComment(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateCommentListSuccess() throws Exception {
		boolean valid = validator.validateCommentList(mockCommentList());
		assertTrue(valid);
	}

	@Test
	public void testValidateCommentListNullFailure() throws Exception {
		boolean valid = validator.validateCommentList(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateCommentListEmptyFailure() throws Exception {
		boolean valid = validator.validateCommentList(Collections.emptyList());
		assertFalse(valid);
	}

	@Test
	public void testValidateCommentListInvalidCommentIdFailure() throws Exception {
		Comment comment = mockComment();
		comment.setCommentId(0L);
		boolean valid = validator.validateCommentList(mockCommentList(comment));
		assertFalse(valid);
	}

	@Test
	public void testValidateCommentListInvalidBlogIdFailure() throws Exception {
		Comment comment = mockComment();
		comment.setBlogId(0L);
		boolean valid = validator.validateCommentList(mockCommentList(comment));
		assertFalse(valid);
	}

	@Test
	public void testValidateCommentListInvalidBlogPostIdFailure() throws Exception {
		Comment comment = mockComment();
		comment.setBlogPostId(0L);
		boolean valid = validator.validateCommentList(mockCommentList(comment));
		assertFalse(valid);
	}
}
