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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Class Account.
 */
@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Account {

	/** The account id. */
	@Id
	@GeneratedValue
	@Column(name = "account_id")
	private Long accountId;

	/** The account created date. */
	@Column(name = "account_created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@CreationTimestamp
	private Date accountCreatedDate;

	/** The account terminated date. */
	@Column(name = "account_terminated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
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
	@LastModifiedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@UpdateTimestamp
	private Date lastUpdatedDate;

	/** The user. */
	@JsonIgnore
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private User user;

	/** The blogs. */
	@JsonIgnore
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
}