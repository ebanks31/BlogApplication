package com.blog.application.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.model.elasticsearch.BlogEs;
import com.blog.application.service.IBlogEsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class sets up the controller for the Account REST end points
 */
@RestController
public class ElasticSearchRestController2 {
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchRestController2.class);
	private static final String BLOG_ID = "blogId: {}";

	@Autowired
	IBlogEsService blogEsService;

	/**
	 * Gets the blogs.
	 *
	 * @param user the user
	 * @return the blogs
	 */
	@GetMapping("/blogsEs")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of blogs"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<BlogEs>> getBlogs() {
		List<BlogEs> blogs = blogEsService.findAll();
		LOGGER.info("blogsEs: {}", blogs);

		return new ResponseEntity<>(blogs, HttpStatus.OK);
	}

	/**
	 * Gets the blog by id.
	 *
	 * @param blogId the blog id
	 * @return the blog by id
	 */
	@GetMapping(value = "/blogsEs/blog/{blogId}", produces = "application/json")
	@ApiOperation(value = "Retreives the blog by blog id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<BlogEs> getBlogById(@PathVariable("blogId") int blogId) {
		LOGGER.info(BLOG_ID, blogId);
		BlogEs blog = blogEsService.findByBlogId(blogId);
		LOGGER.info("blog: {}", blog);

		return new ResponseEntity<>(blog, HttpStatus.OK);
	}

	/**
	 * Adds the blog.
	 *
	 * @param blog the blog
	 * @return the response entity
	 */
	@PostMapping(value = "/blogsEs/blog/add")
	@ApiOperation(value = "Adds a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> addBlog(@RequestBody BlogEs blog) {
		LOGGER.info("blog: {}", blog);
		LOGGER.info("blog.getBlogTitle(): {}", blog.getBlogTitle());

		blogEsService.addBlog(blog);
		return new ResponseEntity<>("Blog has been added successfully", HttpStatus.OK);
	}

	/**
	 * Edits the blog.
	 *
	 * @param blogId the blog id
	 * @param blog   the blog
	 * @return the response entity
	 */
	@PutMapping(value = "/blogsEs/blog/edit/{blogId}")
	@ApiOperation(value = "Edits a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> editBlog(@PathVariable("blogId") Long blogId, @RequestBody BlogEs blog) {
		LOGGER.info(BLOG_ID, blogId);

		blogEsService.editBlog(blogId, blog);
		return new ResponseEntity<>("Blog has been edited successfully", HttpStatus.OK);
	}

	/**
	 * Delete blog post.
	 *
	 * @param blogId the blog id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/blogsEs/blog/delete/{blogId}")
	@ApiOperation(value = "Delete a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> deleteBlogPost(@PathVariable("blogId") Long blogId) {
		LOGGER.info(BLOG_ID, blogId);

		blogEsService.deleteBlog(blogId);
		return new ResponseEntity<>("Blog post has been deleted", HttpStatus.OK);
	}

	/**
	 * Gets the blog by id and user id.
	 *
	 * @param blogPostId the blog post id
	 * @param userId     the user id
	 * @return the blog by id and user id
	 */
	@GetMapping("/blogEs/{blogId}/{userId}")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<BlogEs>> getBlogByIdAndUserId(@PathVariable("blogId") int blogPostId,
			@PathVariable("userId") int userId) {
		List<BlogEs> blog = blogEsService.findAll();
		return new ResponseEntity<>(blog, HttpStatus.OK);
	}
}