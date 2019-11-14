package com.blog.application.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class BlogPost.
 */
@Entity
@Table(name = "blog_post")
public class BlogPost {

	/** The blog post id. */
	@Id
	@GeneratedValue
	@Column(name = "blog_post_id", columnDefinition = "BINARY(16)")
	private Long blogPostId;

	/** The blog post created date. */
	@Column(name = "blog_post_created_date")
	private Date blogPostCreatedDate;

	/** The blog post terminated date. */
	@Column(name = "blog_post_terminated_date")
	private Date blogPostTerminatedDate;

	/** The status. */
	private String status;

	/** The last update date. */
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

	/** The blogPostBody. */
	@Column(name = "blog_post_body")
	private String blogPostBody;

	/** The blogPostTitle. */
	@Column(name = "blog_post_title")
	private String blogPostTitle;

	/** The blog. */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "blog_id", nullable = false, insertable = false, updatable = false)
	private BlogPost blog;

	/** The blog id. */
	@JsonIgnore
	@Column(name = "blog_id", columnDefinition = "BINARY(16)")
	private Long blogId;

	/** The version. */
	@Column(name = "version_num")
	@Version
	private int version;

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * Gets the blog id.
	 *
	 * @return the blog id
	 */
	public Long getBlogId() {
		return blogId;
	}

	/**
	 * Sets the blog id.
	 *
	 * @param blogId the new blog id
	 */
	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	/**
	 * Gets the blog post body.
	 *
	 * @return the blog post body
	 */
	public String getBlogPostBody() {
		return blogPostBody;
	}

	/**
	 * Gets the blog post title.
	 *
	 * @return the blog post title
	 */
	public String getBlogPostTitle() {
		return blogPostTitle;
	}

	/**
	 * Sets the blog post title.
	 *
	 * @param blogPostTitle the new blog post title
	 */
	public void setBlogPostTitle(String blogPostTitle) {
		this.blogPostTitle = blogPostTitle;
	}

	/**
	 * Sets the blog post body.
	 *
	 * @param blogPostBody the new blog post body
	 */
	public void setBlogPostBody(String blogPostBody) {
		this.blogPostBody = blogPostBody;
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
	 * Gets the blog post created date.
	 *
	 * @return the blog post created date
	 */
	public Date getBlogPostCreatedDate() {
		return blogPostCreatedDate;
	}

	/**
	 * Sets the blog post created date.
	 *
	 * @param blogPostCreatedDate the new blog post created date
	 */
	public void setBlogPostCreatedDate(Date blogPostCreatedDate) {
		this.blogPostCreatedDate = blogPostCreatedDate;
	}

	/**
	 * Gets the blog post terminated date.
	 *
	 * @return the blog post terminated date
	 */
	public Date getBlogPostTerminatedDate() {
		return blogPostTerminatedDate;
	}

	/**
	 * Sets the blog post terminated date.
	 *
	 * @param blogPostTerminatedDate the new blog post terminated date
	 */
	public void setBlogPostTerminatedDate(Date blogPostTerminatedDate) {
		this.blogPostTerminatedDate = blogPostTerminatedDate;
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

	/**
	 * Gets the blog.
	 *
	 * @return the blog
	 */
	public BlogPost getBlog() {
		return blog;
	}

	/**
	 * Sets the blog.
	 *
	 * @param blog the new blog
	 */
	public void setBlog(BlogPost blog) {
		this.blog = blog;
	}
}