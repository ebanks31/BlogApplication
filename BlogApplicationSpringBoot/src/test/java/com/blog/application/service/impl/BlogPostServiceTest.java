package com.blog.application.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.blog.application.model.BlogPost;

public class BlogPostServiceTest extends ServiceOperations {

	@Test
	public void testFindAll() throws Exception {
		doNothing().when(blogPostRepository.findAll());
		blogPostService.findAll();
		assertTrue(true);
	}

	@Test
	public void testFindByBlogId() throws Exception {
		when(blogPostRepository.findById(1L)).thenReturn(Optional.of(mockBlogPost()));

		BlogPost resultBlogPost = blogPostService.findByBlogPostId(1);
		assertNotNull(resultBlogPost);

		assertEquals(new Long(1), resultBlogPost.getBlogPostId());
		assertEquals("BlogPostTitle", resultBlogPost.getBlogPostTitle());
		assertEquals("BlogPostBody", resultBlogPost.getBlogPostBody());
		assertEquals("TestStatus", resultBlogPost.getStatus());
	}

	@Test
	public void testFindByBlogPostIdAndBlogId() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testFindByBlogPostId() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testAddBlogPost() throws Exception {
		doNothing().when(blogPostRepository).save(Mockito.any());
		blogPostService.addBlogPost(mockBlogPost(), 1L);
		assertTrue(true);
	}

	@Test
	public void testDeleteBlogPost() throws Exception {
		doNothing().when(blogPostRepository).delete(Mockito.any());
		blogPostService.deleteBlogPost(1L, 1L);
		assertTrue(true);
	}

	@Test
	public void testEditBlogPost() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

}
