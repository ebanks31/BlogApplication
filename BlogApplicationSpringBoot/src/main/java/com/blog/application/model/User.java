package com.blog.application.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The Class User.
 */
@Entity
@Table(name = "user")
public class User {

	/** The user id. */
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long userId;

	/** The firstname. */
	private String firstname;

	/** The lastname. */
	private String lastname;

	/** The middlename. */
	private String middlename;

	/** The last updated date. */
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;

	/** The accounts. */
	@OneToMany(mappedBy = "user")
	private Set<Account> accounts;

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
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the firstname.
	 *
	 * @param firstname the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gets the lastname.
	 *
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the lastname.
	 *
	 * @param lastname the new lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gets the middlename.
	 *
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename;
	}

	/**
	 * Sets the middlename.
	 *
	 * @param middlename the new middlename
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
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
	 * Gets the accounts.
	 *
	 * @return the accounts
	 */
	public Set<Account> getAccounts() {
		return accounts;
	}

	/**
	 * Sets the accounts.
	 *
	 * @param accounts the new accounts
	 */
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
}