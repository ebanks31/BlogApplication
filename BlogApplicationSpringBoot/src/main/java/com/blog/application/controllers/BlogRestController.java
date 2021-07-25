package com.blog.application.controllers;

import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.client.RestTemplate;

import com.blog.application.exception.BlogException;
import com.blog.application.model.Blog;
import com.blog.application.service.IBlogService;
import com.blog.application.service.IEmailService;
import com.blog.application.service.IKafkaService;
import com.blog.application.validator.BlogValidator;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class BlogController.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class BlogRestController {
	private static final String BLOG_VALIDATION_HAS_FAILED = "Blog validation has failed";
	/** The LOGGER. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogRestController.class);
	private static final String BLOG2 = "blog: {}";
	private static final String BLOG_ID = "blogId: {}";
	private final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// private final HazelcastInstance hazelcastInstance;

	@Autowired
	BlogValidator blogValidator;

	/** The blog service. */
	@Autowired
	IBlogService blogService;

	@Autowired
	private RestTemplate restTemplate;

	@Value(value = "${spring.boot.kafka.address}")
	private String springbootKafkaAddress;

	@Value(value = "${project.name}")
	private String projectName;

	@Autowired
	private static IEmailService emailService;

	@Value(value = "${spring.email.addresses}")
	private static String emailAddresses;

	@Value(value = "${spring.boot.kafka.send:false}")
	private static boolean sendToKafka;

	@Autowired
	IKafkaService kafkaservice;

	/*
	 * @Autowired BlogRestController(HazelcastInstance hazelcastInstance) {
	 * this.hazelcastInstance = hazelcastInstance; }
	 */

	/**
	 * Gets the blogs.
	 *
	 * @param user the user
	 * @return the blogs
	 * @throws UnknownHostException
	 * @throws BlogException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/blogs")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of blogs"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<List<Blog>> getBlogs() throws UnknownHostException, BlogException {
		long startTime = System.currentTimeMillis();
		// emailService.sendSimpleMessage("k4polo@gmail.com", "Hello", "World");

		List<Blog> blogs = blogService.findAll();
		boolean validation = blogValidator.validateBlogList(blogs);

		LOGGER.info("blogs: {}", blogs);

		if (validation) {
			if (sendToKafka) {
				kafkaservice.sendBlogsToKafka(startTime, blogs);
			}
			return new ResponseEntity<>(blogs, HttpStatus.OK);
		} else {
			throw new BlogException(BLOG_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Gets the blog by id.
	 *
	 * @param blogId the blog id
	 * @return the blog by id
	 * @throws BlogException
	 */
	@GetMapping(value = "/blogs/blog/{blogId}", produces = "application/json")
	@ApiOperation(value = "Retrieves the blog by blog id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<Blog> getBlogById(@PathVariable("blogId") int blogId) throws BlogException {
		LOGGER.info(BLOG_ID, blogId);
		Blog blog = blogService.findByBlogId(blogId);

		boolean validation = blogValidator.validateBlog(blog);

		LOGGER.info(BLOG2, blog);

		if (validation) {
			return new ResponseEntity<>(blog, HttpStatus.OK);
		} else {
			// Throw exception here. The Exception Advise will handle the response output
			throw new BlogException(BLOG_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Adds the blog.
	 *
	 * @param blog the blog
	 * @return the response entity
	 * @throws BlogException
	 */
	@PostMapping(value = "/blogs/blog/add")
	@ApiOperation(value = "Adds a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> addBlog(@RequestBody Blog blog) throws BlogException {
		LOGGER.info(BLOG2, blog);
		LOGGER.info("blog.getBlogTitle(): {}", blog.getBlogTitle());

		boolean validation = blogValidator.validateBlog(blog);

		if (validation) {
			blogService.addBlog(blog);
			return new ResponseEntity<>("Blog has been added successfully", HttpStatus.OK);
		} else {
			// Throw exception here. The Exception Advise will handle the response output
			throw new BlogException(BLOG_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Edits the blog.
	 *
	 * @param blogId the blog id
	 * @param blog   the blog
	 * @return the response entity
	 * @throws BlogException
	 */
	@PutMapping(value = "/blogs/blog/edit/{blogId}")
	@ApiOperation(value = "Edits a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> editBlog(@PathVariable("blogId") Long blogId, @RequestBody Blog blog)
			throws BlogException {
		LOGGER.info(BLOG_ID, blogId);
		boolean validation = blogValidator.validateBlog(blog);

		if (validation) {
			blogService.editBlog(blogId, blog);
			return new ResponseEntity<>("Blog has been edited successfully", HttpStatus.OK);
		} else {
			// Throw exception here. The Exception Advise will handle the response output
			throw new BlogException(BLOG_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Delete blog post.
	 *
	 * @param blogId the blog id
	 * @return the response entity
	 * @throws BlogException
	 */
	@DeleteMapping(value = "/blogs/blog/delete/{blogId}")
	@ApiOperation(value = "Delete a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<String> deleteBlogPost(@PathVariable("blogId") Long blogId) throws BlogException {
		LOGGER.info(BLOG_ID, blogId);
		boolean validation = blogValidator.validateNumber(blogId);

		if (validation) {
			blogService.deleteBlog(blogId);
			return new ResponseEntity<>("Blog post has been deleted", HttpStatus.OK);
		} else {
			// Throw exception here. The Exception Advise will handle the response output
			throw new BlogException(BLOG_VALIDATION_HAS_FAILED);
		}
	}

	/**
	 * Gets the blog by id and user id.
	 *
	 * @param blogPostId the blog post id
	 * @param userId     the user id
	 * @return the blog by id and user id
	 * @throws BlogException
	 */
	@GetMapping("/blog/{blogId}/{userId}")
	@ApiOperation(value = "View a list of blogs", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 503, message = "Service Unavailable") })
	public ResponseEntity<List<Blog>> getBlogByIdAndUserId(@PathVariable("blogId") int blogPostId,
			@PathVariable("userId") int userId) throws BlogException {
		List<Blog> blogList = blogService.findAll();
		boolean validation = blogValidator.validateBlogList(blogList);

		if (validation) {
			return new ResponseEntity<>(blogList, HttpStatus.OK);
		} else {
			// Throw exception here. The Exception Advise will handle the response output
			throw new BlogException(BLOG_VALIDATION_HAS_FAILED);
		}
	}
}