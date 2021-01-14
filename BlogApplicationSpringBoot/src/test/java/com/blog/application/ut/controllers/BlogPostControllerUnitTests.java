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

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.blog.application.model.BlogPost;
import com.google.gson.Gson;

public class BlogPostControllerUnitTests extends TestControllerOperations {

	/**
	 * Gets the blog posts one blog post.
	 *
	 * @return the blog posts one blog post
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogPostsOneBlogPost() throws Exception {
		BlogPost blogPost = new BlogPost();
		blogPost.setBlogPostId(new Long(1));
		blogPost.setBlogPostTitle("blogPostTitle");
		blogPost.setBlogPostBody("blogPostBody");

		List<BlogPost> blogPosts = Arrays.asList(blogPost);

		long value = 1;
		when(blogPostService.findByBlogId(value)).thenReturn(blogPosts);

		mockMvc.perform(
				get("/blogs/blog/{blogId}/posts", 1).header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].blogPostId", is(1)))
				.andExpect(jsonPath("$[0].blogPostTitle", is("blogPostTitle")))
				.andExpect(jsonPath("$[0].blogPostBody", is("blogPostBody")));
	}

	/**
	 * Gets the blog posts test multiple blog posts.
	 *
	 * @return the blog posts test multiple blog posts
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogPostsTestMultipleBlogPosts() throws Exception {
		BlogPost firstBlogPost = new BlogPost();
		firstBlogPost.setBlogPostId(new Long(1));
		firstBlogPost.setBlogPostTitle("blogPostTitle");
		firstBlogPost.setBlogPostBody("blogPostBody");

		BlogPost secondBlogPost = new BlogPost();
		secondBlogPost.setBlogPostId(new Long(2));
		secondBlogPost.setBlogPostTitle("blogTitle1");
		secondBlogPost.setBlogPostBody("blogDescription1");

		List<BlogPost> blogPosts = Arrays.asList(firstBlogPost, secondBlogPost);

		when(blogPostService.findByBlogId(1)).thenReturn(blogPosts);

		mockMvc.perform(
				get("/blogs/blog/{blogId}/posts", 1).header(ORIGIN, "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].blogPostId", is(1)))
				.andExpect(jsonPath("$[0].blogPostTitle", is("blogPostTitle")))
				.andExpect(jsonPath("$[0].blogPostBody", is("blogPostBody")))
				.andExpect(jsonPath("$[1].blogPostId", is(2)))
				.andExpect(jsonPath("$[1].blogPostTitle", is("blogTitle1")))
				.andExpect(jsonPath("$[1].blogPostBody", is("blogDescription1")));
	}

	/**
	 * Gets the blog posts test no blog post.
	 *
	 * @return the blog posts test no blog post
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogPostsTestNoBlogPost() throws Exception {
		BlogPost blogPost = new BlogPost();
		blogPost.setBlogPostId(new Long(1));
		blogPost.setBlogPostTitle("blogPostTitle");
		blogPost.setBlogPostBody("blogPostBody");

		List<BlogPost> blogs = Arrays.asList();

		when(blogPostService.findByBlogId(1)).thenReturn(blogs);

		ResultActions resultActions = mockMvc
				.perform(get("/blogs/blog/{blogPostId}/posts", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Gets the blog posts test blog post null.
	 *
	 * @return the blog posts test blog post null
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogPostsTestBlogPostNull() throws Exception {
		BlogPost blogPost = new BlogPost();
		blogPost.setBlogPostId(new Long(1));
		blogPost.setBlogPostTitle("blogPostTitle");
		blogPost.setBlogPostBody("blogPostBody");

		List<BlogPost> blogs = null;

		when(blogPostService.findByBlogId(1)).thenReturn(blogs);

		ResultActions resultActions = mockMvc
				.perform(get("/blogs/blog/{blogPostId}/posts", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Adds the blog post test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void addBlogPostTest() throws Exception {
		BlogPost blogPost = mockBlogPost();

		Gson gson = new Gson();
		String blogJson = gson.toJson(blogPost);

		doNothing().when(blogPostService).addBlogPost(blogPost, blogPost.getBlogId());

		ResultActions resultActions = mockMvc
				.perform(post("/blogs/blog/{blogId}/posts/post/add", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON).content(blogJson))
				.andExpect(content().string(containsString("Blog post has been added successfully")))
				.andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Delete blog post test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteBlogPostTest() throws Exception {
		BlogPost blogPost = mockBlogPost();

		Gson gson = new Gson();
		String blogJson = gson.toJson(blogPost);

		doNothing().when(blogPostService).deleteBlogPost(blogPost.getBlogPostId(), blogPost.getBlogId());

		ResultActions resultActions = mockMvc
				.perform(delete("/blogs/blog/1/posts/post/delete/{blogPostId}", 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON).content(blogJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Edits the blog post test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void editBlogPostTest() throws Exception {
		BlogPost blogPost = mockBlogPost();

		Gson gson = new Gson();
		String blogJson = gson.toJson(blogPost);

		doNothing().when(blogPostService).editBlogPost(blogPost.getBlogPostId(), blogPost.getBlogId(), blogPost);

		ResultActions resultActions = mockMvc
				.perform(put("/blogs/blog/{blogId}/posts/post/edit/{blogPostId}", 1, 1).header(ORIGIN, "*")
						.contentType(MediaType.APPLICATION_JSON).content(blogJson))
				.andExpect(content().string(containsString(""))).andExpect(status().isOk());

		resultActions.andDo(MockMvcResultHandlers.print());
	}

	/**
	 * Gets the blog post by id and blog Id.
	 *
	 * @return the blog post by id
	 * @throws Exception the exception
	 */
	@Test
	public void getBlogPostByIdAndBlogId() throws Exception {
		BlogPost blogPost = mockBlogPost();

		Gson gson = new Gson();
		String accountJson = gson.toJson(blogPost);

		when(blogPostService.findByBlogPostIdAndBlogId(blogPost.getBlogPostId(), blogPost.getBlogId()))
				.thenReturn(blogPost);

		mockMvc.perform(get("/blogs/blog/{blogId}/posts/{blogPostId}", 1, 1).header(ORIGIN, "*")
				.contentType(MediaType.APPLICATION_JSON).content(accountJson)).andExpect(status().isOk())
				.andExpect(jsonPath("$.blogPostId", is(1))).andExpect(jsonPath("$.blogPostTitle", is("blogPostTitle")))
				.andExpect(jsonPath("$.blogPostBody", is("blogPostBody")));
	}
}