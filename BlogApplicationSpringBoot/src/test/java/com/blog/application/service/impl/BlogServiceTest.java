package com.blog.application.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;

import com.blog.application.model.Blog;

public class BlogServiceTest extends ServiceOperations {

	@Test
	public void testFindAllBlogs() throws Exception {
		when(blogCacheService.findAllBlogsFromCache()).thenReturn(null);
		when(blogRepository.findAll()).thenReturn(mockBlogList());

		List<Blog> blogList = blogService.findAll();
		assertNotNull(blogList);
		Blog resultBlog = blogList.get(0);

		assertEquals(new Long(1), resultBlog.getBlogId());
		assertEquals("blogTitle", resultBlog.getBlogTitle());
		assertEquals("blogDescription", resultBlog.getBlogDescription());
	}

	@Test
	public void testFindAllBlogsFromCache() throws Exception {
		when(blogCacheService.findAllBlogsFromCache()).thenReturn(mockBlogList());
		List<Blog> blogList = blogService.findAll();
		assertNotNull(blogList);
		Blog resultBlog = blogList.get(0);

		assertEquals(new Long(1), resultBlog.getBlogId());
		assertEquals("blogTitle", resultBlog.getBlogTitle());
		assertEquals("blogDescription", resultBlog.getBlogDescription());

	}

	@Test
	public void testFindByBlogId() throws Exception {
		when(blogCacheService.findAllBlogsFromCache()).thenReturn(null);
		when(blogRepository.findById(1L)).thenReturn(Optional.of(mockBlog()));

		Blog resultBlog = blogService.findByBlogId(1);
		assertNotNull(resultBlog);

		assertEquals(new Long(1), resultBlog.getBlogId());
		assertEquals("blogTitle", resultBlog.getBlogTitle());
		assertEquals("blogDescription", resultBlog.getBlogDescription());
	}

	@Test
	public void testAddBlog() throws Exception {
		when(blogRepository.save(Mockito.any())).thenReturn(mockBlog());
		blogService.addBlog(mockBlog());
		assertTrue(true);
	}

	@Test
	public void testDeleteBlog() throws Exception {
		doNothing().when(blogRepository).delete(Mockito.any());
		blogService.deleteBlog(1L);
		assertTrue(true);
	}

	@Test
	public void testEditBlog() throws Exception {
		when(blogRepository.findByBlogPostId(1L)).thenReturn(Optional.of(mockBlog()));
		when(blogRepository.save(Mockito.any())).thenReturn(mockBlog());

		blogService.editBlog(1L, mockBlog());
		assertTrue(true);
	}

	@Test
	public void testEditBlogAccountEmpty() throws Exception {
		when(blogRepository.findByBlogPostId(1L)).thenReturn(Optional.empty());
		when(blogRepository.save(Mockito.any())).thenReturn(mockBlog());

		blogService.editBlog(1L, mockBlog());
		assertTrue(true);
	}

	@Test
	public void testEditBlogNoBlogPostIdFound() throws Exception {
		when(blogRepository.findByBlogPostId(1L)).thenReturn(Optional.empty());
		when(blogRepository.save(Mockito.any())).thenReturn(mockBlog());

		blogService.editBlog(1L, mockBlog());
		assertTrue(true);
	}
}
