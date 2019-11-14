package com.blog.application.model.elasticsearch;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Entity bean with JPA annotations for User table. Hibernate provides JPA
 * implementation
 *
 * @author ebanks
 *
 */
@Document(indexName = "blogs", type = "blog")
public class BlogEs {

	@Id
	private String _id = UUID.randomUUID().toString();

	/** The blog id. */
	private Long blog_id;

	/** The blog created date. */
	private Date blogCreatedDate;

	/** The blog terminated date. */
	private Date blogTerminatedDate;

	/** The status. */
	private String status;

	/** The blogDescription. */
	@Column(name = "blog_description")
	private String blogDescription;

	/** The blogTitle. */
	private String blogTitle;

	/** The last update date. */
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

	/**
	 * Gets the blog title.
	 *
	 * @return the blog title
	 */
	public String getBlogTitle() {
		return blogTitle;
	}

	/**
	 * Sets the blog title.
	 *
	 * @param blogTitle the new blog title
	 */
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	/**
	 * Gets the blog id.
	 *
	 * @return the blog id
	 */
	public Long getBlogId() {
		return blog_id;
	}

	/**
	 * Sets the blog id.
	 *
	 * @param blogId the new blog id
	 */
	public void setBlogId(Long blogId) {
		this.blog_id = blogId;
	}

	/**
	 * Gets the blog created date.
	 *
	 * @return the blog created date
	 */
	public Date getBlogCreatedDate() {
		return blogCreatedDate;
	}

	/**
	 * Sets the blog created date.
	 *
	 * @param blogCreatedDate the new blog created date
	 */
	public void setBlogCreatedDate(Date blogCreatedDate) {
		this.blogCreatedDate = blogCreatedDate;
	}

	/**
	 * Gets the blog terminated date.
	 *
	 * @return the blog terminated date
	 */
	public Date getBlogTerminatedDate() {
		return blogTerminatedDate;
	}

	/**
	 * Sets the blog terminated date.
	 *
	 * @param blogTerminatedDate the new blog terminated date
	 */
	public void setBlogTerminatedDate(Date blogTerminatedDate) {
		this.blogTerminatedDate = blogTerminatedDate;
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
	 * Gets the blog description.
	 *
	 * @return the blog description
	 */
	public String getBlogDescription() {
		return blogDescription;
	}

	/**
	 * Sets the blog description.
	 *
	 * @param blogDescription the new blog description
	 */
	public void setBlogDescription(String blogDescription) {
		this.blogDescription = blogDescription;
	}

	/**
	 * Gets the last update date.
	 *
	 * @return the last update date
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * Sets the last update date.
	 *
	 * @param lastUpdateDate the new last update date
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}