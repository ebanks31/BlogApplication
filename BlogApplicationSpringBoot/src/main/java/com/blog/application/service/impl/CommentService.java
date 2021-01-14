package com.blog.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.application.model.Comment;
import com.blog.application.repositories.CommentRepository;
import com.blog.application.service.ICommentService;

/**
 * The Class CommentService.
 */
@Service
public class CommentService implements ICommentService {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

	/** The repository. */
	@Autowired
	private CommentRepository repository;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.blog.application.service.ICommentService#findAll()
	 */
	@Override
	public List<Comment> findAll() {
		return repository.findAll();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.blog.application.service.ICommentService#findByCommentId(long)
	 */
	@Override
	public Comment findByCommentId(long commentId) {

		Optional<Comment> commentOptional = repository.findById(commentId);
		Comment retrievedComment = null;

		if (commentOptional.isPresent()) {
			retrievedComment = commentOptional.get();
		}

		return retrievedComment;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.blog.application.service.ICommentService#addComment(com.blog.application.
	 * model.Comment)
	 */
	@Override
	public void addComment(Comment comment) {
		repository.save(comment);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.blog.application.service.ICommentService#deleteComment(long)
	 */
	@Override
	public void deleteComment(long commentId) {
		repository.deleteById(commentId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.blog.application.service.ICommentService#editComment(long)
	 */
	@Override
	public void editComment(long commentId, Comment comment) {
		if (commentId != 0) {
			Optional<Comment> commentOptional = repository.findById(commentId);
			Comment retrievedComment = null;

			if (commentOptional.isPresent()) {
				retrievedComment = commentOptional.get();
			}

			if (retrievedComment != null) {
				retrievedComment.setComment(comment.getComment());

				repository.save(retrievedComment);
			} else {
				LOGGER.info("No comment was found");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.blog.application.service.ICommentService#findByCommentId(long)
	 */
	@Override
	public Comment findByCommentByBlogIdAndBlogPostId(long commentId, long blogId, long blogPostId) {
		Optional<Comment> commentOptional = repository.findCommentByBlogIdAndBlogPostId(commentId, blogId, blogPostId);
		Comment retrievedComment = null;

		if (commentOptional.isPresent()) {
			retrievedComment = commentOptional.get();
		}

		return retrievedComment;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.blog.application.service.ICommentService#addComment(com.blog.application.
	 * model.Comment)
	 */
	@Override
	public void addCommentWithBlogIdAndBlogPostId(Comment comment, long blogId, long blogPostId) {
		comment.setBlogId(blogId);
		comment.setBlogPostId(blogPostId);
		repository.save(comment);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.blog.application.service.ICommentService#addComment(com.blog.application.
	 * model.Comment)
	 */
	@Override
	public void deleteCommentWithBlogIdAndBlogPostId(long commentId, long blogId, long blogPostId) {
		repository.deleteCommentByBlogIdAndBlogPostId(commentId, blogId, blogPostId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.blog.application.service.ICommentService#editCommentByBlogIdAndBlogPostId
	 * (long)
	 */
	@Override
	public void editCommentByBlogIdAndBlogPostId(Comment comment, long commentId, long blogId, long blogPostId) {
		if (commentId != 0) {
			Optional<Comment> commentOptional = repository.findCommentByBlogIdAndBlogPostId(commentId, blogId,
					blogPostId);
			Comment retrievedComment = null;

			if (commentOptional.isPresent()) {
				retrievedComment = commentOptional.get();
			}

			if (retrievedComment != null) {
				retrievedComment.setComment(comment.getComment());
				repository.save(retrievedComment);
			} else {
				LOGGER.info("No comment was found");
			}
		}
	}
}
