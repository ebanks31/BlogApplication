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
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.blog.application.model.Blog;
import com.google.gson.Gson;

public class BlogControllerUnitTests extends TestOperations {

	@Before()
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
	}

	/**
	 * Gets the blogs one blog.
	 *
	 * @return the blogs one blog
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogsOneBlog() throws Exception {
		Blog blog = mockBlog();

		List<Blog> blogs = Arrays.asList(blog);

		when(blogService.findAll()).thenReturn(blogs);

		mockMvc.perform(get("/blogs").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
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
		Blog firstBlog = mockBlog();

		Blog secondBlog = new Blog();
		secondBlog.setBlogId(new Long(2));
		secondBlog.setBlogTitle("blogTitle1");
		secondBlog.setBlogDescription("blogDescription1");

		List<Blog> blogs = Arrays.asList(firstBlog, secondBlog);

		when(blogService.findAll()).thenReturn(blogs);

		mockMvc.perform(get("/blogs").header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
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
		when(blogService.findAll()).thenReturn(Collections.emptyList());

		ResultActions resultActions = mockMvc
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
		when(blogService.findAll()).thenReturn(null);

		ResultActions resultActions = mockMvc
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
		Blog blog = mockBlog();

		Gson gson = new Gson();
		String blogJson = gson.toJson(blog);

		doNothing().when(blogService).addBlog(blog);

		ResultActions resultActions = mockMvc
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
		Blog blog = mockBlog();

		Gson gson = new Gson();
		String blogJson = gson.toJson(blog);

		doNothing().when(blogService).deleteBlog(1);

		ResultActions resultActions = mockMvc
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
		Blog blog = mockBlog();

		Gson gson = new Gson();
		String blogJson = gson.toJson(blog);

		doNothing().when(blogService).editBlog(1, blog);

		ResultActions resultActions = mockMvc.perform(put("/blogs/blog/edit/{blogId}", 1).header(ORIGIN, "*")
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
		Blog blog = mockBlog();

		Gson gson = new Gson();
		String accountJson = gson.toJson(blog);

		when(blogService.findByBlogId(1)).thenReturn(blog);

		mockMvc.perform(get("/blogs/blog/{blogId}", 1).header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON)
				.content(accountJson)).andExpect(status().isOk()).andExpect(jsonPath("$.blogId", is(1)))
				.andExpect(jsonPath("$.blogTitle", is("blogTitle")))
				.andExpect(jsonPath("$.blogDescription", is("blogDescription")));
	}
}