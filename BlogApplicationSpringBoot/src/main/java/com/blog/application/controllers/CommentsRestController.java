package com.blog.application.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.exception.BlogException;
import com.blog.application.model.Comment;
import com.blog.application.service.ICommentService;
import com.blog.application.validator.CommentValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class BlogRestController.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/blogs/blog")
public class CommentsRestController {
	private static final String COMMENT_VALIDATION_HAS_FAILED = "Comment validation has failed";
	private static final String BLOG_POST_ID2 = "blogPostId";
	private static final String BLOG_ID = "blogId: {}";
	private static final String BLOG_POST_ID = "blogPostId: {}";

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentsRestController.class);

	/** The blog post service. */
	@Autowired
	ICommentService commentService;

	@Autowired
	CommentValidator commentValidator;

	/**
	 * Gets the blog post comments.
	 *
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 * @return the blog post comments
	 * @throws BlogException
	 */
	@GetMapping(value = "/{blogId}/posts/{blogPostId}/comments", produces = "application/json")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the comments"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<List<Comment>> getBlogPostComments(@PathVariable("blogId") long blogId,
			@PathVariable(BLOG_POST_ID2) long blogPostId) throws BlogException {
		LOGGER.info(BLOG_ID, blogId);
		LOGGER.info(BLOG_POST_ID, blogPostId);

		boolean validBlogId = commentValidator.validateNumber(blogId);
		boolean validBlogPostId = commentValidator.validateNumber(blogPostId);

		if (validBlogId && validBlogPostId) {
			List<Comment> comments = commentService.findAll();
			LOGGER.info("comments {}", comments);

			return new ResponseEntity<>(comments, HttpStatus.OK);
		} else {
			throw new BlogException(COMMENT_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Adds the blog post comments.
	 *
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 * @param comment    the comment
	 * @return the response entity
	 * @throws BlogException
	 */
	@PostMapping(value = "/{blogId}/posts/{blogPostId}/comments/add", produces = "application/json")
	@ApiOperation(value = "Adds a comment", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the comment"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> addBlogPostComments(@PathVariable("blogId") long blogId,
			@PathVariable(BLOG_POST_ID2) long blogPostId, @RequestBody Comment comment) throws BlogException {
		LOGGER.info(BLOG_ID, blogId);
		LOGGER.info(BLOG_POST_ID, blogPostId);

		commentService.addComment(comment);

		boolean validBlogId = commentValidator.validateNumber(blogId);
		boolean validBlogPostId = commentValidator.validateNumber(blogPostId);
		boolean validComment = commentValidator.validateComment(comment);

		if (validBlogId && validBlogPostId && validComment) {
			commentService.addCommentWithBlogIdAndBlogPostId(comment, blogId, blogPostId);
			return new ResponseEntity<>("Successfully added the comment", HttpStatus.OK);
		} else {
			throw new BlogException(COMMENT_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Edits the blog post comments.
	 *
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 * @param commentId  the comment id
	 * @return the response entity
	 * @throws BlogException
	 */
	@PutMapping(value = "/{blogId}/posts/{blogPostId}/comments/edit/{commentId}", produces = "application/json")
	@ApiOperation(value = "Edits a comment", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited the comment"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> editBlogPostComments(@PathVariable("blogId") long blogId,
			@PathVariable(BLOG_POST_ID2) long blogPostId, @PathVariable("commentId") long commentId,
			@RequestBody Comment comment) throws BlogException {
		LOGGER.info(BLOG_ID, blogId);
		LOGGER.info(BLOG_POST_ID, blogPostId);

		boolean validBlogId = commentValidator.validateNumber(blogId);
		boolean validBlogPostId = commentValidator.validateNumber(blogPostId);
		boolean validComment = commentValidator.validateComment(comment);

		if (validBlogId && validBlogPostId && validComment) {
			commentService.editCommentByBlogIdAndBlogPostId(comment, commentId, blogId, blogPostId);
			return new ResponseEntity<>("Successfully edited the comment", HttpStatus.OK);
		} else {
			throw new BlogException(COMMENT_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Deletes the blog post comments.
	 *
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 * @param commentId  the comment id
	 * @return the response entity
	 * @throws BlogException
	 */
	@DeleteMapping(value = "/{blogId}/posts/{blogPostId}/comments/delete/{commentId}", produces = "application/json")
	@ApiOperation(value = "Deletes a blog post", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted the comment"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> deleteBlogPostComments(@PathVariable("blogId") long blogId,
			@PathVariable(BLOG_POST_ID2) long blogPostId, @PathVariable("commentId") long commentId)
			throws BlogException {
		LOGGER.info(BLOG_ID, blogId);
		LOGGER.info(BLOG_POST_ID, blogPostId);

		boolean validBlogId = commentValidator.validateNumber(blogId);
		boolean validBlogPostId = commentValidator.validateNumber(blogPostId);

		if (validBlogId && validBlogPostId) {
			commentService.deleteCommentWithBlogIdAndBlogPostId(commentId, blogId, blogPostId);
			return new ResponseEntity<>("Successfully deleted the comment", HttpStatus.OK);
		} else {
			throw new BlogException(COMMENT_VALIDATION_HAS_FAILED);
		}
	}
}