package com.blog.application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.blog.application.model.BlogPost;

@RepositoryRestResource
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

	@Modifying
	@Transactional
	@Query(value = "delete from BlogPost t where blog_post_id= ?1 and blog_id = ?2")
	void deleteByBlogPostIdAndBlogId(long blogPostId, long blogId);

	@Query(value = "select * from Blog_Post t where t.blog_id = ?1", nativeQuery = true)
	List<BlogPost> findAllBlogPostsByBlogId(long blogId);

	@Query(value = "select * from Blog_Post t where t.blog_id = ?1 and t.blog_post_id = ?2 LIMIT 1", nativeQuery = true)
	BlogPost findByBlogPostIdAndBlogId(long blogId, long blogPostId);

	@Query(value = "select * from Blog_Post t where t.blog_post_id = ?1 LIMIT 1", nativeQuery = true)
	BlogPost findByBlogPostId(Long blogPostId);
}
