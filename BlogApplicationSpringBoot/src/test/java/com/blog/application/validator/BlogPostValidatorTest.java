package com.blog.application.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;
import org.mockito.InjectMocks;

import com.blog.application.model.Blog;
import com.blog.application.model.BlogPost;
import com.blog.application.service.impl.ServiceOperations;

public class BlogPostValidatorTest extends ServiceOperations {

	@InjectMocks
	BlogPostValidator validator;

	@Test
	public void testValidateBlogPostSuccess() throws Exception {
		boolean valid = validator.validateBlogPost(mockBlogPost());
		assertTrue(valid);
	}

	@Test
	public void testValidateBlogPostNullFailure() throws Exception {
		boolean valid = validator.validateBlogPost(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogPostBlogPostIdInvalidFailure() throws Exception {
		BlogPost blogPost = mockBlogPost();
		blogPost.setBlogPostId(0L);
		boolean valid = validator.validateBlogPost(blogPost);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogPostBlogIdInvalidFailure() throws Exception {
		BlogPost blogPost = mockBlogPost();
		Blog blog = mockBlog();
		blog.setBlogId(0L);

		blogPost.setBlog(blog);
		boolean valid = validator.validateBlogPost(blogPost);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogPostListSuccess() throws Exception {
		boolean valid = validator.validateBlogPostList(mockBlogPostList());
		assertTrue(valid);
	}

	@Test
	public void testValidateBlogPostListNullFailure() throws Exception {
		boolean valid = validator.validateBlogPostList(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogPostListEmptyFailure() throws Exception {
		boolean valid = validator.validateBlogPostList(Collections.emptyList());
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogListBlogPostIdInvalidFailure() throws Exception {
		BlogPost blogPost = mockBlogPost();
		blogPost.setBlogPostId(0L);
		boolean valid = validator.validateBlogPost(blogPost);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogListBlogIdInvalidFailure() throws Exception {
		BlogPost blogPost = mockBlogPost();
		Blog blog = mockBlog();
		blog.setBlogId(0L);

		blogPost.setBlog(blog);
		boolean valid = validator.validateBlogPost(blogPost);
		assertFalse(valid);
	}

}
