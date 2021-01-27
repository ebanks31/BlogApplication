package com.blog.application.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.blog.application.model.BlogPost;

public class BlogPostServiceTest extends ServiceOperations {

	@Test
	public void testFindAll() throws Exception {
		when(blogPostRepository.findAll()).thenReturn(mockBlogPostList());
		List<BlogPost> blogPostList = blogPostService.findAll();
		assertNotNull(blogPostList);

		BlogPost firstBlogPost = blogPostList.get(0);

		assertEquals(new Long(1), firstBlogPost.getBlogPostId());
		assertEquals("BlogPostTitle", firstBlogPost.getBlogPostTitle());
		assertEquals("BlogPostBody", firstBlogPost.getBlogPostBody());
		assertEquals("TestStatus", firstBlogPost.getStatus());
	}

	@Test
	public void testFindByBlogPostId() throws Exception {
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
		when(blogPostRepository.findByBlogPostIdAndBlogId(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(mockBlogPost());

		BlogPost resultBlogPost = blogPostService.findByBlogPostIdAndBlogId(1L, 1L);
		assertNotNull(resultBlogPost);

		assertEquals(new Long(1), resultBlogPost.getBlogPostId());
		assertEquals("BlogPostTitle", resultBlogPost.getBlogPostTitle());
		assertEquals("BlogPostBody", resultBlogPost.getBlogPostBody());
		assertEquals("TestStatus", resultBlogPost.getStatus());
	}

	@Test
	public void testFindByBlogPostIdAndBlogIdNull() throws Exception {
		when(blogPostRepository.findByBlogPostIdAndBlogId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(null);

		BlogPost resultBlogPost = blogPostService.findByBlogPostIdAndBlogId(1L, 1L);
		assertNull(resultBlogPost);
	}

	@Test
	public void testAddBlogPost() throws Exception {
		when(blogPostRepository.save(Mockito.any())).thenReturn(mockBlogPost());
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
		when(blogPostRepository.findByBlogPostId(1L)).thenReturn(mockBlogPost());
		when(blogPostRepository.save(Mockito.any())).thenReturn(mockBlogPost());

		blogPostService.editBlogPost(1L, 1L, mockBlogPost());
		assertTrue(true);
	}
}
