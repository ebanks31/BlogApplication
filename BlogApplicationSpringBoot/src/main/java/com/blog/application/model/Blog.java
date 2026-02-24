package com.blog.application.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
 * The Class Blog.
 */
@Entity
@Table(name = "blog")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
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
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@CreationTimestamp
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
	@LastModifiedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@UpdateTimestamp
	private Date lastUpdateDate;

	/** The blog posts. */
	@JsonIgnore
	@OneToMany(mappedBy = "blog", fetch = FetchType.LAZY)
	private Set<BlogPost> blogPosts;

	/** The status. */
	@Column(name = "account_id")
	private Long accountId;
}
