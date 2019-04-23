package com.blog.application.ut.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.blog.application.controllers.BlogRestController;
import com.blog.application.model.Blog;
import com.blog.application.service.IBlogService;
import com.google.gson.Gson;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountController.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BlogRestController.class)
public class BlogControllerUnitTests {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(BlogControllerUnitTests.class);

	/** The Constant ORIGIN. */
	private static final String ORIGIN = "origin";

	/** The mvc. */
	@Autowired
	private MockMvc mvc;

	/** The blog service. */
	@MockBean
	IBlogService blogService;

	/**
	 * Sets the up.
	 */
	@Before()
	public void setUp() {

	}

	/**
	 * Gets the blogs one blog.
	 *
	 * @return the blogs one blog
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogsOneBlog() throws Exception {
		LOGGER.info("getBlogsOneAccount()");

		Blog blog = new Blog();
		blog.setBlogId(new Long(1));
		blog.setBlogTitle("blogTitle");
		blog.setBlogDescription("blogDescription");

		List<Blog> blogs = Arrays.asList(blog);

		when(blogService.findAll()).thenReturn(blogs);

		mvc.perform(get("/blogs").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].blogId", is(1))).andExpect(jsonPath("$[0].blogTitle", is("blogTitle")))
				.andExpect(jsonPath("$[0].blogDescription", is("blogDescription")));
	}

	/**
	 * Gets the blogs test multiple blogs.
	 *
	 * @return the blogs test multiple blogs
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogsTestMultipleBlogs() throws Exception {
		LOGGER.info("getBlogsTestMultipleAccount()");

		Blog firstBlog = new Blog();
		firstBlog.setBlogId(new Long(1));
		firstBlog.setBlogTitle("blogTitle");
		firstBlog.setBlogDescription("blogDescription");

		Blog secondBlog = new Blog();
		secondBlog.setBlogId(new Long(2));
		secondBlog.setBlogTitle("blogTitle1");
		secondBlog.setBlogDescription("blogDescription1");

		List<Blog> blogs = Arrays.asList(firstBlog, secondBlog);

		when(blogService.findAll()).thenReturn(blogs);

		mvc.perform(get("/blogs").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].blogId", is(1))).andExpect(jsonPath("$[0].blogTitle", is("blogTitle")))
				.andExpect(jsonPath("$[0].blogDescription", is("blogDescription")))
				.andExpect(jsonPath("$[1].blogId", is(2))).andExpect(jsonPath("$[1].blogTitle", is("blogTitle1")))
				.andExpect(jsonPath("$[1].blogDescription", is("blogDescription1")));
	}

	/**
	 * Gets the blogs test no blog.
	 *
	 * @return the blogs test no blog
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogsTestNoBlog() throws Exception {
		LOGGER.info("getBlogsTestNoAccount()");

		Blog blog = new Blog();
		blog.setBlogId(new Long(1));
		blog.setBlogTitle("blogTitle");
		blog.setBlogDescription("blogDescription");

		List<Blog> blogs = Arrays.asList();

		when(blogService.findAll()).thenReturn(blogs);

		ResultActions resultActions = mvc
				.perform(get("/blogs").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Gets the blogs test blog null.
	 *
	 * @return the blogs test blog null
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogsTestBlogNull() throws Exception {
		LOGGER.info("getBlogsTestAccountNull()");

		Blog blog = new Blog();
		blog.setBlogId(new Long(1));
		blog.setBlogTitle("blogTitle");
		blog.setBlogDescription("blogDescription");

		List<Blog> blogs = null;

		when(blogService.findAll()).thenReturn(blogs);

		ResultActions resultActions = mvc
				.perform(get("/blogs").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Adds the blog test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void addBlogTest() throws Exception {
		LOGGER.info("addBlogTest()");

		Blog blog = new Blog();
		blog.setBlogId(new Long(1));
		blog.setBlogTitle("blogTitle");
		blog.setBlogDescription("blogDescription");

		Gson gson = new Gson();
		String blogJson = gson.toJson(blog);

		doNothing().when(blogService).addBlog(blog);

		ResultActions resultActions = mvc
				.perform(post("/blogs/blog/add").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
						.content(blogJson))
				.andExpect(content().string(containsString("Blog has been added successfully")))
				.andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Delete blog test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteBlogTest() throws Exception {
		LOGGER.info("deleteBlogTest()");

		Blog blog = new Blog();
		blog.setBlogId(new Long(1));
		blog.setBlogTitle("blogTitle");
		blog.setBlogDescription("blogDescription");

		Gson gson = new Gson();
		String blogJson = gson.toJson(blog);

		doNothing().when(blogService).deleteBlog(1);

		ResultActions resultActions = mvc
				.perform(delete("/blogs/blog/delete/{blogId}", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON).content(blogJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Edits the blog test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void editBlogTest() throws Exception {
		LOGGER.info("editBlogTest()");

		Blog blog = new Blog();
		blog.setBlogId(new Long(1));
		blog.setBlogTitle("blogTitle");
		blog.setBlogDescription("blogDescription");

		Gson gson = new Gson();
		String blogJson = gson.toJson(blog);

		doNothing().when(blogService).editBlog(1, blog);

		ResultActions resultActions = mvc.perform(put("/blogs/blog/edit/{blogId}", 1).header(ORIGIN, "*")
				.contentType(MediaType.APPLICATION_JSON).content(blogJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Gets the blog by id.
	 *
	 * @return the blog by id
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogById() throws Exception {
		LOGGER.info("getBlogById()");

		Blog blog = new Blog();
		blog.setBlogId(new Long(1));
		blog.setBlogTitle("blogTitle");
		blog.setBlogDescription("blogDescription");

		Gson gson = new Gson();
		String accountJson = gson.toJson(blog);

		when(blogService.findByBlogId(1)).thenReturn(blog);

		mvc.perform(get("/blogs/blog/{blogId}", 1).header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
				.content(accountJson)).andExpect(status().isOk()).andExpect(jsonPath("$.blogId", is(1)))
				.andExpect(jsonPath("$.blogTitle", is("blogTitle")))
				.andExpect(jsonPath("$.blogDescription", is("blogDescription")));
	}
}