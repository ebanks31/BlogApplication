package com.blog.application.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class Blog.
 */
@Entity
@Table(name = "blog")
public class Blog {

	/** The blog post id. */
	@Id
	@GeneratedValue
	@Column(name = "blog_id", columnDefinition = "BINARY(16)")
	private Long blogId;

	/** The account id. */
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false, insertable = false, updatable = false)
	private Account account;

	/** The blog created date. */
	@Column(name = "blog_created_date")
	private Date blogCreatedDate;

	/** The blog terminated date. */
	@Column(name = "blog_terminated_date")
	private Date blogTerminatedDate;

	/** The status. */
	@Column(name = "status")
	private String status;

	/** The blogDescription. */
	@Column(name = "blog_description")
	private String blogDescription;

	/** The blogDescription. */
	@Column(name = "blog_title")
	private String blogTitle;

	/** The last update date. */
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;

	/** The accounts. */
	@JsonIgnore
	@OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
	private Set<BlogPost> blogPosts;

	/** The status. */
	@Column(name = "account_id")
	private Long accountId;

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

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
	 * Gets the account.
	 *
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Sets the account.
	 *
	 * @param account the new account
	 */
	public void setAccount(Account account) {
		this.account = account;
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

	/**
	 * Gets the blog posts.
	 *
	 * @return the blog posts
	 */
	public Set<BlogPost> getBlogPosts() {
		return blogPosts;
	}

	/**
	 * Sets the blog posts.
	 *
	 * @param blogPosts the new blog posts
	 */
	public void setBlogPosts(Set<BlogPost> blogPosts) {
		this.blogPosts = blogPosts;
	}

}
