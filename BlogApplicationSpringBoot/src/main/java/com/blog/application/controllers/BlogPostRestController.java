package com.blog.application.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.blog.application.model.BlogPost;
import com.blog.application.service.IBlogPostService;
import com.blog.application.validator.BlogPostValidator;
import com.google.gson.Gson;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class BlogRestController.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/blogs/blog")
public class BlogPostRestController {
	private static final String THE_BLOG_POST_IS_INVALID = "The blog post is invalid";

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogPostRestController.class);
	private static final Gson gson = new Gson();
	private static final String BLOG_POST_ID_LOG = "blogPostId: {}";
	private static final String BLOG_ID_LOG = "blog Id: {}";

	/** The blog post service. */
	@Autowired
	IBlogPostService blogPostService;

	@Autowired
	BlogPostValidator blogPostValidator;

	/**
	 * Gets the blog posts by blog id.
	 *
	 * @param blogId the blog id
	 * @return the blog posts by blog id
	 * @throws BlogException
	 */
	@GetMapping(value = "/{blogId}/posts", produces = "application/json")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of blog posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<List<BlogPost>> getBlogPostsByBlogId(@PathVariable("blogId") long blogId)
			throws BlogException {
		LOGGER.info("blogId:  {}", blogId);
		List<BlogPost> blogposts = blogPostService.findByBlogId(blogId);
		LOGGER.info(BLOG_POST_ID_LOG, blogposts);

		boolean valid = blogPostValidator.validateNumber(blogId);

		if (valid) {
			return new ResponseEntity<>(blogposts, HttpStatus.OK);
		} else {
			throw new BlogException(THE_BLOG_POST_IS_INVALID);
		}
	}

	/**
	 * Gets the blog by id.
	 *
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 * @return the blog by id
	 * @throws BlogException
	 */
	@GetMapping(value = "/{blogId}/posts/{blogPostId}", produces = "application/json")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<BlogPost> getBlogPostByBlogPostIdAndBlogId(@PathVariable("blogId") long blogId,
			@PathVariable("blogPostId") long blogPostId) throws BlogException {
		LOGGER.info(BLOG_ID_LOG, blogId);
		LOGGER.info(BLOG_POST_ID_LOG, blogPostId);
		boolean validBlogId = blogPostValidator.validateNumber(blogId);
		boolean validBlogPostId = blogPostValidator.validateNumber(blogPostId);

		if (validBlogId && validBlogPostId) {
			BlogPost blogpost = blogPostService.findByBlogPostIdAndBlogId(blogId, blogPostId);
			LOGGER.info("blog post {}", blogpost);
			return new ResponseEntity<>(blogpost, HttpStatus.OK);
		} else {
			throw new BlogException(THE_BLOG_POST_IS_INVALID);
		}
	}

	/**
	 * Adds the blog post.
	 *
	 * @param blogpost the blogpost
	 * @return the response entity
	 * @throws BlogException
	 */
	@PostMapping(value = "/{blogId}/posts/post/add")
	@ApiOperation(value = "Adds a new blog Post", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> addBlogPost(@RequestBody BlogPost blogPost, @PathVariable("blogId") long blogId)
			throws BlogException {
		LOGGER.info("addBlogPost");

		LOGGER.info("blogpost: {}", blogPost);
		boolean validBlogPost = blogPostValidator.validateBlogPost(blogPost);
		boolean validBlogId = blogPostValidator.validateNumber(blogId);

		if (validBlogPost && validBlogId) {
			blogPostService.addBlogPost(blogPost, blogId);
			return new ResponseEntity<>(gson.toJson("Blog post has been added successfully"), HttpStatus.OK);
		} else {
			throw new BlogException(THE_BLOG_POST_IS_INVALID);
		}
	}

	/**
	 * Edits the blog post.
	 *
	 * @param blogpost   the blogpost
	 * @param blogPostId the blog post id
	 * @return the response entity
	 * @throws BlogException
	 */
	@PutMapping(value = "/{blogId}/posts/post/edit/{blogPostId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Editas a blog post", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> editBlogPost(@PathVariable("blogPostId") Long blogPostId,
			@PathVariable("blogId") Long blogId, @RequestBody BlogPost blogPost) throws BlogException {
		LOGGER.info("editBlogPost");
		LOGGER.info(BLOG_POST_ID_LOG, blogPostId);

		boolean validBlogPost = blogPostValidator.validateBlogPost(blogPost);
		boolean validBlogId = blogPostValidator.validateNumber(blogId);

		if (validBlogPost && validBlogId) {
			blogPostService.editBlogPost(blogPostId, blogId, blogPost);
			return new ResponseEntity<>("Blog post has been edited successfully", HttpStatus.OK);
		} else {
			throw new BlogException(THE_BLOG_POST_IS_INVALID);
		}

	}

	/**
	 * Deletes the blog post.
	 *
	 * @param blogpost   the blogpost
	 * @param blogPostId the blog post id
	 * @return the response entity
	 * @throws BlogException
	 */
	@DeleteMapping(value = "/{blogId}/posts/post/delete/{blogPostId}")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> deleteBlogPost(@PathVariable("blogPostId") Long blogPostId,
			@PathVariable("blogId") Long blogId) throws BlogException {
		LOGGER.info(BLOG_POST_ID_LOG, blogPostId);
		LOGGER.info(BLOG_ID_LOG, blogId);

		boolean validBlogId = blogPostValidator.validateNumber(blogId);
		boolean validBlogPostId = blogPostValidator.validateNumber(blogPostId);

		if (validBlogId && validBlogPostId) {
			blogPostService.deleteBlogPost(blogPostId, blogId);
			return new ResponseEntity<>("Blog post has been deleted", HttpStatus.OK);
		} else {
			throw new BlogException(THE_BLOG_POST_IS_INVALID);
		}
	}
}