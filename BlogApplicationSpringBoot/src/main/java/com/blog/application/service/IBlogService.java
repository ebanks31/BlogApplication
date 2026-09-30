package com.blog.application.service;

import java.util.List;

import com.blog.application.model.Blog;

/**
 * Defines business operations for blog records.
 *
 * <p>Implementations provide CRUD behavior for {@link Blog} entities and
 * coordinate any blog-specific caching or persistence concerns.</p>
 */
public interface IBlogService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Blog> findAll();

	/**
	 * Find by blog id.
	 *
	 * @param blogId the blog id
	 * @return the blog
	 */
	public Blog findByBlogId(long blogId);

	/**
	 * Adds the blog.
	 *
	 * @param blog the blog
	 */
	public void addBlog(Blog blog);

	/**
	 * Edits the blog.
	 *
	 * @param blogId the blog id
	 * @param blog   the blog
	 */
	public void editBlog(long blogId, Blog blog);

	/**
	 * Delete blog.
	 *
	 * @param blogId the blog id
	 */
	public void deleteBlog(long blogId);
}