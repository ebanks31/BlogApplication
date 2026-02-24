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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

// TODO: Auto-generated Javadoc
/**
 * The Class Comment .
 */
@Entity
@Table(name = "comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Comment {

	/** The comment id. */
	@Id
	@GeneratedValue
	@Column(name = "comment_id")
	private Long commentId;

	/** The comment. */
	private String comment;

	/** The blog id. */
	@Column(name = "blog_id")
	private Long blogId;

	/** The blog post id. */
	@Column(name = "blog_post_id")
	private Long blogPostId;

	/** The comment created date. */
	@Column(name = "comment_created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@CreationTimestamp
	private Date commentCreatedDate;

	/** The comment deleted date. */
	@Column(name = "comment_deleted_date")
	private Date commentDeletedDate;

	/** The status. */
	private String status;

	/** The last updated date. */
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@UpdateTimestamp
	private Date lastUpdatedDate;
}
