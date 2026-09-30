package com.blog.application.ut.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.blog.application.controllers.CommentsRestController;
import com.blog.application.model.Comment;
import com.blog.application.service.ICommentService;
import com.blog.application.validator.CommentValidator;
import com.google.gson.Gson;

/** Unit tests for the comments REST controller. */
public class CommentsControllerUnitTests {

	private static final String COMMENTS_PATH = "/blogs/blog/1/posts/2/comments";

	@InjectMocks
	private CommentsRestController commentsController;

	@Mock
	private ICommentService commentService;

	@Mock
	private CommentValidator commentValidator;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(commentsController).build();
	}

	@Test
	public void getCommentsReturnsCommentsForValidBlogPost() throws Exception {
		Comment comment = comment(3L, "A comment");
		when(commentValidator.validateNumber(1L)).thenReturn(true);
		when(commentValidator.validateNumber(2L)).thenReturn(true);
		when(commentService.findAll()).thenReturn(Collections.singletonList(comment));

		mockMvc.perform(get(COMMENTS_PATH).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].commentId", is(3)))
				.andExpect(jsonPath("$[0].comment", is("A comment")));
	}

	@Test
	public void addCommentDelegatesWithParentIdentifiers() throws Exception {
		Comment comment = comment(0L, "A comment");
		when(commentValidator.validateNumber(1L)).thenReturn(true);
		when(commentValidator.validateNumber(2L)).thenReturn(true);
		when(commentValidator.validateComment(org.mockito.ArgumentMatchers.any(Comment.class))).thenReturn(true);

		mockMvc.perform(post(COMMENTS_PATH + "/add").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(comment)))
				.andExpect(status().isOk());

		verify(commentService).addComment(org.mockito.ArgumentMatchers.any(Comment.class));
		verify(commentService).addCommentWithBlogIdAndBlogPostId(org.mockito.ArgumentMatchers.any(Comment.class),
				1L, 2L);
	}

	@Test
	public void editCommentDelegatesWithAllIdentifiers() throws Exception {
		Comment comment = comment(3L, "Updated comment");
		when(commentValidator.validateNumber(1L)).thenReturn(true);
		when(commentValidator.validateNumber(2L)).thenReturn(true);
		when(commentValidator.validateComment(org.mockito.ArgumentMatchers.any(Comment.class))).thenReturn(true);

		mockMvc.perform(put(COMMENTS_PATH + "/edit/3").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(comment)))
				.andExpect(status().isOk());

		verify(commentService).editCommentByBlogIdAndBlogPostId(org.mockito.ArgumentMatchers.any(Comment.class), 3L,
				1L, 2L);
	}

	@Test
	public void deleteCommentDelegatesWithAllIdentifiers() throws Exception {
		when(commentValidator.validateNumber(1L)).thenReturn(true);
		when(commentValidator.validateNumber(2L)).thenReturn(true);

		mockMvc.perform(delete(COMMENTS_PATH + "/delete/3")).andExpect(status().isOk());

		verify(commentService).deleteCommentWithBlogIdAndBlogPostId(3L, 1L, 2L);
	}

	private Comment comment(long commentId, String text) {
		Comment comment = new Comment();
		comment.setCommentId(commentId);
		comment.setComment(text);
		comment.setStatus("ACTIVE");
		return comment;
	}
}
