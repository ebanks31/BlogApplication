package com.blog.application.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The Class Comment .
 */
@Entity
@Table(name = "comment")
public class Comment {

	/** The comment id. */
	@Id
	@GeneratedValue
	@Column(name = "comment_id")
	private Long commentId;

	/** The comment. */
	private String comment;

	/** The blog post id. */
	private Long blogPostId;

	/** The comment created date. */
	@Column(name = "comment_created_date")
	private Date commentCreatedDate;

	/** The comment deleted date. */
	@Column(name = "comment_deleted_date")
	private Date commentDeletedDate;

	/** The status. */
	private String status;

	/** The last updated date. */
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;

	/**
	 * Gets the comment id.
	 *
	 * @return the comment id
	 */
	public Long getCommentId() {
		return commentId;
	}

	/**
	 * Sets the comment id.
	 *
	 * @param commentId the new comment id
	 */
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the blog post id.
	 *
	 * @return the blog post id
	 */
	public Long getBlogPostId() {
		return blogPostId;
	}

	/**
	 * Sets the blog post id.
	 *
	 * @param blogPostId the new blog post id
	 */
	public void setBlogPostId(Long blogPostId) {
		this.blogPostId = blogPostId;
	}

	/**
	 * Gets the comment created date.
	 *
	 * @return the comment created date
	 */
	public Date getCommentCreatedDate() {
		return commentCreatedDate;
	}

	/**
	 * Sets the comment created date.
	 *
	 * @param commentCreatedDate the new comment created date
	 */
	public void setCommentCreatedDate(Date commentCreatedDate) {
		this.commentCreatedDate = commentCreatedDate;
	}

	/**
	 * Gets the comment deleted date.
	 *
	 * @return the comment deleted date
	 */
	public Date getCommentDeletedDate() {
		return commentDeletedDate;
	}

	/**
	 * Sets the comment deleted date.
	 *
	 * @param commentDeletedDate the new comment deleted date
	 */
	public void setCommentDeletedDate(Date commentDeletedDate) {
		this.commentDeletedDate = commentDeletedDate;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the last updated date.
	 *
	 * @return the last updated date
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * Sets the last updated date.
	 *
	 * @param lastUpdatedDate the new last updated date
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

}
