package com.blog.application.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.blog.application.model.BlogPost;
import com.blog.application.service.impl.ServiceOperations;

public class BlogPostValidatorTest extends ServiceOperations {

	@Autowired
	BlogPostValidator validator;

	@Test
	public void testValidateBlogPostSuccess() throws Exception {
		boolean valid = validator.validateBlogPost(mockBlogPost());
		assertTrue(valid);
	}

	@Test
	public void testValidateBlogPostNullFailure() throws Exception {
		boolean valid = validator.validateBlogPost(null);
		assertTrue(valid);
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
		blogPost.setBlogId(0L);
		boolean valid = validator.validateBlogPost(blogPost);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogPostListSuccess() throws Exception {
		boolean valid = validator.validateBlogPostList(mockBlogPostList());
		assertTrue(valid);
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
		blogPost.setBlogId(0L);
		boolean valid = validator.validateBlogPost(blogPost);
		assertFalse(valid);
	}

}
