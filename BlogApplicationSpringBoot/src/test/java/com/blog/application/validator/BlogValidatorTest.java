package com.blog.application.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.blog.application.model.Blog;
import com.blog.application.service.impl.ServiceOperations;

public class BlogValidatorTest extends ServiceOperations {

	@InjectMocks
	BlogValidator validator;

	@Test
	public void testValidateBlogSuccess() throws Exception {
		boolean valid = validator.validateBlog(mockBlog());
		assertTrue(valid);
	}

	@Test
	public void testValidateBlogNullFailure() throws Exception {
		boolean valid = validator.validateBlog(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogBlogIdInvalidFailure() throws Exception {
		Blog blog = mockBlog();
		blog.setBlogId(0L);
		boolean valid = validator.validateBlog(blog);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogAccountIdInvalidFailure() throws Exception {
		Blog blog = mockBlog();
		blog.setAccountId(0L);
		boolean valid = validator.validateBlog(blog);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogTitleNullFailure() throws Exception {
		Blog blog = mockBlog();
		blog.setBlogTitle(null);
		boolean valid = validator.validateBlog(blog);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogTitleEmptyFailure() throws Exception {
		Blog blog = mockBlog();
		blog.setBlogTitle(StringUtils.EMPTY);
		boolean valid = validator.validateBlog(blog);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogListSuccess() throws Exception {
		boolean valid = validator.validateBlogList(mockBlogList());
		assertTrue(valid);
	}

	@Test
	public void testValidateBlogListNullFailure() throws Exception {
		boolean valid = validator.validateBlogList(null);
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogListEmptyFailure() throws Exception {
		boolean valid = validator.validateBlogList(Collections.emptyList());
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogListBlogIdInvalidFailure() throws Exception {
		Blog blog = mockBlog();
		blog.setBlogId(0L);
		boolean valid = validator.validateBlogList(mockBlogList(blog));
		assertFalse(valid);
	}

	@Test
	public void testValidateBlogListAccountIdInvalidFailure() throws Exception {
		Blog blog = mockBlog();
		blog.setAccountId(0L);
		boolean valid = validator.validateBlogList(mockBlogList(blog));
		assertFalse(valid);
	}
}
