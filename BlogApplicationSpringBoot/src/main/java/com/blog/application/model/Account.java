package com.blog.application.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * The Class Account.
 */
@Entity
@Table(name = "account")
public class Account {

	/** The account id. */
	@Id
	@GeneratedValue
	@Column(name = "account_id")
	private Long accountId;

	/** The account created date. */
	@Column(name = "account_created_date")
	private Date accountCreatedDate;

	/** The account terminated date. */
	@Column(name = "account_terminated_date")
	private Date accountTerminatedDate;

	/** The status. */
	@Column(name = "status")
	private String status;

	/** The role. */
	@Column(name = "role")
	private String role;

	/** The last updated date. */
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;

	/** The user. */
	@JsonIgnore
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private User user;

	/** The blogs. */
	@OneToMany(mappedBy = "account")
	private Set<Blog> blogs;

	/** The user id. */
	@Column(name = "user_id")
	private Long userId;

	/** The email. */
	private String email;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Long getAccountId() {
		return accountId;
	}

	/**
	 * Gets the blogs.
	 *
	 * @return the blogs
	 */
	public Set<Blog> getBlogs() {
		return blogs;
	}

	/**
	 * Sets the blogs.
	 *
	 * @param blogs the new blogs
	 */
	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the new account id
	 */
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the account created date.
	 *
	 * @return the account created date
	 */
	public Date getAccountCreatedDate() {
		return accountCreatedDate;
	}

	/**
	 * Sets the account created date.
	 *
	 * @param accountCreatedDate the new account created date
	 */
	public void setAccountCreatedDate(Date accountCreatedDate) {
		this.accountCreatedDate = accountCreatedDate;
	}

	/**
	 * Gets the account terminated date.
	 *
	 * @return the account terminated date
	 */
	public Date getAccountTerminatedDate() {
		return accountTerminatedDate;
	}

	/**
	 * Sets the account terminated date.
	 *
	 * @param accountTerminatedDate the new account terminated date
	 */
	public void setAccountTerminatedDate(Date accountTerminatedDate) {
		this.accountTerminatedDate = accountTerminatedDate;
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
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
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

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
