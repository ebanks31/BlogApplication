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
import org.springframework.web.bind.annotation.RestController;

import com.blog.application.model.Blog;
import com.blog.application.model.User;
import com.blog.application.service.IBlogService;
import com.blog.application.service.ICommentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class BlogController.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class BlogRestController {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogRestController.class);

	/** The blog service. */
	@Autowired
	IBlogService blogService;

	/** The comment service. */
	@Autowired
	ICommentService commentService;

	/**
	 * Gets the blogs.
	 *
	 * @param user the user
	 * @return the blogs
	 */
	@GetMapping("/blogs")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of blogs"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<Blog>> getBlogs(User user) {
		List<Blog> blog = blogService.findAll();
		LOGGER.info("blog: {}", blog);

		return new ResponseEntity<>(blog, HttpStatus.OK);
	}

	/**
	 * Gets the blog by id.
	 *
	 * @param blogId the blog id
	 * @return the blog by id
	 */
	@GetMapping(value = "/blogs/blog/{blogId}", produces = "application/json")
	@ApiOperation(value = "Retreives the blog by blog id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<Blog> getBlogById(@PathVariable("blogId") int blogId) {
		LOGGER.info("blogId: {}", blogId);
		Blog blog = blogService.findByBlogId(blogId);
		LOGGER.info("blog: {}", blog);
		LOGGER.info("blog: {}", blog.getAccount().getStatus());

		return new ResponseEntity<>(blog, HttpStatus.OK);
	}

	@PostMapping(value = "/blogs/blog/add")
	@ApiOperation(value = "Adds a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> addBlog(@RequestBody Blog blog) {
		LOGGER.info("blog: {}", blog);
		LOGGER.info("blog.getBlogTitle(): {}", blog.getBlogTitle());

		blogService.addBlog(blog);
		return new ResponseEntity<>("Blog has been added successfully", HttpStatus.OK);
	}

	@PutMapping(value = "/blogs/blog/edit/{blogId}")
	@ApiOperation(value = "Edits a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> editBlog(@PathVariable("blogId") Long blogId, @RequestBody Blog blog) {
		LOGGER.info("blogId: {}", blogId);

		blogService.editBlog(blogId, blog);
		return new ResponseEntity<>("Blog has been edited successfully", HttpStatus.OK);
	}

	@DeleteMapping(value = "/blogs/blog/delete/{blogId}")
	@ApiOperation(value = "Delete a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> deleteBlogPost(@PathVariable("blogId") Long blogId) {
		LOGGER.info("blogId: {}", blogId);

		blogService.deleteBlog(blogId);
		return new ResponseEntity<>("Blog post has been deleted", HttpStatus.OK);
	}

	/**
	 * Gets the blog by id and user id.
	 *
	 * @param blogPostId the blog post id
	 * @param userId     the user id
	 * @return the blog by id and user id
	 */
	@GetMapping("/blog/{blogId}/{userId}")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<Blog>> getBlogByIdAndUserId(@PathVariable("blogId") int blogPostId,
			@PathVariable("userId") int userId) {
		List<Blog> blog = blogService.findAll();
		return new ResponseEntity<>(blog, HttpStatus.OK);
	}

}