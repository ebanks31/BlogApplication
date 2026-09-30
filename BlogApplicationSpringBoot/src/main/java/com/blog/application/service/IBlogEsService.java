package com.blog.application.service;

import java.util.List;

import com.blog.application.model.elasticsearch.BlogEs;

/**
 * Defines blog search operations for the Elasticsearch-backed model.
 *
 * <p>Implementations provide retrieval and persistence operations for
 * {@link BlogEs} documents.</p>
 */
public interface IBlogEsService {
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<BlogEs> findAll();

	/**
	 * Find by blog id.
	 *
	 * @param blogId the blog id
	 * @return the blog
	 */
	public BlogEs findByBlogId(long blogId);

	/**
	 * Adds the blog.
	 *
	 * @param blog the blog
	 */
	public void addBlog(BlogEs blog);

	/**
	 * Edits the blog.
	 *
	 * @param blogId the blog id
	 * @param blog   the blog
	 */
	public void editBlog(long blogId, BlogEs blog);

	/**
	 * Delete blog.
	 *
	 * @param blogId the blog id
	 */
	public void deleteBlog(long blogId);
}
