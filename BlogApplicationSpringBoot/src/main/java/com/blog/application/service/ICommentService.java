package com.blog.application.service;

import java.util.List;

import com.blog.application.model.Comment;

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
}
