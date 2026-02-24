package com.blog.application.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;

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
 * The Class BlogPost.
 */
@Entity
@Table(name = "blog_post")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BlogPost {

	/** The blog post id. */
	@Id
	@GeneratedValue
	@Column(name = "blog_post_id", columnDefinition = "BINARY(16)")
	private Long blogPostId;

	/** The blog post created date. */
	@Column(name = "blog_post_created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@CreationTimestamp
	private Date blogPostCreatedDate;

	/** The blog post terminated date. */
	@Column(name = "blog_post_terminated_date")
	private Date blogPostTerminatedDate;

	/** The status. */
	private String status;

	/** The last update date. */
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@UpdateTimestamp
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
	@JoinColumn(name = "id", referencedColumnName = "blog_id", nullable = false, insertable = true, updatable = true)
	private Blog blog;

	/** The blog id. */
//	@JsonIgnore
//	@Column(name = "blog_id", columnDefinition = "BINARY(16)")
//	private Long blogId;

	/** The version. */
	@Column(name = "version_num")
	@Version
	private int version;
}