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

import com.blog.application.model.BlogPost;
import com.blog.application.service.IBlogPostService;

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
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogPostRestController.class);

	/** The blog post service. */
	@Autowired
	IBlogPostService blogPostService;

	/**
	 * Gets the blog posts by blog id.
	 *
	 * @param id the id
	 * @return the blog posts by blog id
	 */
	@GetMapping(value = "/{id}/posts", produces = "application/json")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of blog posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<BlogPost>> getBlogPostsByBlogId(@PathVariable("id") long id) {
		LOGGER.info("id: {}", id);
		List<BlogPost> blogposts = blogPostService.findByBlogId(id);
		LOGGER.info("blogposts: {}", blogposts);

		return new ResponseEntity<>(blogposts, HttpStatus.OK);
	}

	/**
	 * Gets the blog by id.
	 *
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 * @return the blog by id
	 */
	@GetMapping(value = "/{blogId}/posts/{blogPostId}", produces = "application/json")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<BlogPost> getBlogPostByBlogPostIdAndBlogId(@PathVariable("blogId") long blogId,
			@PathVariable("blogPostId") long blogPostId) {
		LOGGER.info("blogId: {}", blogId);
		LOGGER.info("blogPostId: {}", blogPostId);

		BlogPost blogpost = blogPostService.findByBlogPostIdAndBlogId(blogId, blogPostId);
		LOGGER.info("blogpost {}", blogpost);

		return new ResponseEntity<>(blogpost, HttpStatus.OK);
	}

	/**
	 * Adds the blog post.
	 *
	 * @param blogpost the blogpost
	 * @return the response entity
	 */
	@PostMapping(value = "/{blogId}/posts/post/add")
	@ApiOperation(value = "Adds a new blog Post", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> addBlogPost(@RequestBody BlogPost blogpost, @PathVariable("blogId") long blogId) {
		LOGGER.info("addBlogPost: {}");

		LOGGER.info("blogpost: {}", blogpost);

		blogPostService.addBlogPost(blogpost);

		return new ResponseEntity<>("Blog post has been added successfully", HttpStatus.OK);
	}

	/**
	 * Edits the blog post.
	 *
	 * @param blogpost   the blogpost
	 * @param blogPostId the blog post id
	 * @return the response entity
	 */
	@PutMapping(value = "/{blogId}/posts/post/edit/{blogPostId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Editas a blog post", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> editBlogPost(@PathVariable("blogPostId") Long blogPostId,
			@RequestBody BlogPost blogPost) {
		LOGGER.info("editBlogPost");

		LOGGER.info("blogPostId: {}", blogPostId);

		blogPostService.editBlogPost(blogPostId, blogPost);
		return new ResponseEntity<>("Blog post has been edited successfully", HttpStatus.OK);
	}

	/**
	 * Deletes the blog post.
	 *
	 * @param blogpost   the blogpost
	 * @param blogPostId the blog post id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/{blogId}/posts/post/delete/{blogPostId}")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> deleteBlogPost(@PathVariable("blogPostId") Long blogPostId) {
		LOGGER.info("blogPostId: {}", blogPostId);

		blogPostService.deleteBlogPost(blogPostId);
		return new ResponseEntity<>("Blog post has been deleted", HttpStatus.OK);
	}
}