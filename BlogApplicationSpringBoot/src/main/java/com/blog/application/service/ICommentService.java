package com.blog.application.service;

import java.util.List;

import com.blog.application.model.Comment;

// TODO: Auto-generated Javadoc
/**
 * The Interface ICommentService.
 */
public interface ICommentService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Comment> findAll();

	/**
	 * Adds the comment.
	 *
	 * @param comment the comment
	 */
	public void addComment(Comment comment);

	/**
	 * Find by comment id.
	 *
	 * @param commentId the comment id
	 * @return the comment
	 */
	public Comment findByCommentId(long commentId);

	/**
	 * Edits the comment.
	 *
	 * @param commentId the comment id
	 * @param comment   the comment
	 */
	public void editComment(long commentId, Comment comment);

	/**
	 * Delete comment.
	 *
	 * @param commentId the comment id
	 */
	public void deleteComment(long commentId);

	/**
	 * Find by comment by blog id and blog post id.
	 *
	 * @param commentId  the comment id
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 * @return the comment
	 */
	public Comment findByCommentByBlogIdAndBlogPostId(long commentId, long blogId, long blogPostId);

	/**
	 * Adds the comment with blog id and blog post id.
	 *
	 * @param comment    the comment
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 */
	public void addCommentWithBlogIdAndBlogPostId(Comment comment, long blogId, long blogPostId);

	/**
	 * Delete comment with blog id and blog post id.
	 *
	 * @param commentId  the comment id
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 */
	public void deleteCommentWithBlogIdAndBlogPostId(long commentId, long blogId, long blogPostId);

	/**
	 * Edits the comment by blog id and blog post id.
	 *
	 * @param comment    the comment
	 * @param commentId  the comment id
	 * @param blogId     the blog id
	 * @param blogPostId the blog post id
	 */
	public void editCommentByBlogIdAndBlogPostId(Comment comment, long commentId, long blogId, long blogPostId);
}
