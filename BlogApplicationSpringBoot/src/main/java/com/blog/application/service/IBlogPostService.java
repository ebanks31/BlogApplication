/*
 * 
 */
package com.blog.application.service;

import java.util.List;

import com.blog.application.model.BlogPost;

/**
 * Defines business operations for blog posts and their parent blogs.
 *
 * <p>Implementations provide CRUD operations and support locating posts by
 * blog ID or by the combined blog and post identifiers.</p>
 */
public interface IBlogPostService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<BlogPost> findAll();

	/**
	 * Find by blog id.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<BlogPost> findByBlogId(long blogpostId);

	/**
	 * Find by blog post id and blog id.
	 *
	 * @param blogId     the blog id
	 * @param blogpostId the blogpost id
	 * @return the blog post
	 */
	public BlogPost findByBlogPostIdAndBlogId(long blogId, long blogpostId);

	/**
	 * Adds the blog post.
	 *
	 * @param blogPost the blog post
	 */
	public void addBlogPost(BlogPost blogPost, long blogId);

	/**
	 * Find by blog post id.
	 *
	 * @param id the id
	 * @return the blog post
	 */
	public BlogPost findByBlogPostId(long blogPostId);

	/**
	 * Delete blog post.
	 *
	 * @param blogPostId the blog post id
	 */
	public void deleteBlogPost(long blogPostId, long blogId);

	/**
	 * Edits the blog post.
	 *
	 * @param blogPostId the blog post id
	 * @param blogPost   the blog post
	 */
	public void editBlogPost(long blogPostId, long blogId, BlogPost blogpost);
}