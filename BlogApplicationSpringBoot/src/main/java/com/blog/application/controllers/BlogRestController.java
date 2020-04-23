package com.blog.application.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.blog.application.model.Blog;
import com.blog.application.service.IBlogService;
import com.blog.application.service.IEmailService;
import com.blog.application.utils.Helper;
import com.hazelcast.core.HazelcastInstance;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class BlogController.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class BlogRestController {
	/** The LOGGER. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogRestController.class);
	private static final String BLOG2 = "blog: {}";
	private static final String BLOG_ID = "blogId: {}";
	private final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private final HazelcastInstance hazelcastInstance;

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

	@Autowired
	Environment environment;

	@Autowired
	BlogRestController(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}

	/**
	 * Gets the blogs.
	 *
	 * @param user the user
	 * @return the blogs
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/blogs")
	@ApiOperation(value = "View a list of blog Posts", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list of blogs"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<List<Blog>> getBlogs() throws UnknownHostException {
		long startTime = System.currentTimeMillis();
		// emailService.sendSimpleMessage("k4polo@gmail.com", "Hello", "World");

		List<Blog> blogs = blogService.findAll();
		LOGGER.info("blogs: {}", blogs);

		HttpHeaders headers = new HttpHeaders();

		// can set the content Type
		headers.setContentType(MediaType.APPLICATION_JSON);
		// put your customBean to header
		JSONObject messageJsonObject = new JSONObject();
		messageJsonObject.put("id_number", 1);
		messageJsonObject.put("hostAddress", InetAddress.getLocalHost().toString());
		messageJsonObject.put("hostName", InetAddress.getLocalHost().getHostName());
		messageJsonObject.put("hostPort", environment.getProperty("server.port"));
		messageJsonObject.put("status", "Failure");
		messageJsonObject.put("method", "getBlogs");
		messageJsonObject.put("urlPath", "/blogs");
		messageJsonObject.put("projectName", "TestProject");
		messageJsonObject.put("statusCode", HttpStatus.OK.value());

		UUID uuid = UUID.randomUUID();
		messageJsonObject.put("uniqueIdentifier", uuid.toString());

		String blogsJSON = Helper.createJson(blogs).toString();

		messageJsonObject.put("details", blogsJSON);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);
		LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
		LOGGER.info("Date time formatter: {}", dateTimeFormatter.format(now));

		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTime;

		LOGGER.info("executeTime: {}", executeTime);
		final float sec;

		sec = executeTime / 1000.0f;
		LOGGER.info("sec: {}", sec);

		messageJsonObject.put("apiTransactionTime", sec);

		HttpEntity<String> entity = new HttpEntity<>(messageJsonObject.toString(), headers);
		restTemplate.postForObject(springbootKafkaAddress, entity, String.class);
		return new ResponseEntity<>(blogs, HttpStatus.OK);
	}

	public static void sendEmail() {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(emailAddresses);
		msg.setSubject("Testing from Spring Boot");
		msg.setText("Hello World \n Spring Boot Email");

		// javaMailSender.send(msg);
	}

	/**
	 * Gets the blog by id.
	 *
	 * @param blogId the blog id
	 * @return the blog by id
	 */
	@GetMapping(value = "/blogs/blog/{blogId}", produces = "application/json")
	@ApiOperation(value = "Retrieves the blog by blog id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<Blog> getBlogById(@PathVariable("blogId") int blogId) {
		LOGGER.info(BLOG_ID, blogId);
		Blog blog = blogService.findByBlogId(blogId);
		LOGGER.info(BLOG2, blog);

		return new ResponseEntity<>(blog, HttpStatus.OK);
	}

	/**
	 * Adds the blog.
	 *
	 * @param blog the blog
	 * @return the response entity
	 */
	@PostMapping(value = "/blogs/blog/add")
	@ApiOperation(value = "Adds a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> addBlog(@RequestBody Blog blog) {
		LOGGER.info(BLOG2, blog);
		LOGGER.info("blog.getBlogTitle(): {}", blog.getBlogTitle());

		blogService.addBlog(blog);
		return new ResponseEntity<>("Blog has been added successfully", HttpStatus.OK);
	}

	/**
	 * Edits the blog.
	 *
	 * @param blogId the blog id
	 * @param blog   the blog
	 * @return the response entity
	 */
	@PutMapping(value = "/blogs/blog/edit/{blogId}")
	@ApiOperation(value = "Edits a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully edited the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> editBlog(@PathVariable("blogId") Long blogId, @RequestBody Blog blog) {
		LOGGER.info(BLOG_ID, blogId);

		blogService.editBlog(blogId, blog);
		return new ResponseEntity<>("Blog has been edited successfully", HttpStatus.OK);
	}

	/**
	 * Delete blog post.
	 *
	 * @param blogId the blog id
	 * @return the response entity
	 */
	@DeleteMapping(value = "/blogs/blog/delete/{blogId}")
	@ApiOperation(value = "Delete a blog", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted the blog post"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<String> deleteBlogPost(@PathVariable("blogId") Long blogId) {
		LOGGER.info(BLOG_ID, blogId);

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
	@ApiOperation(value = "View a list of blogs", response = Iterable.class)
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