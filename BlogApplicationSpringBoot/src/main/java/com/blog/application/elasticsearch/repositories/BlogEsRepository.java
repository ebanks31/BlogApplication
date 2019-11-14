package com.blog.application.elasticsearch.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.blog.application.model.elasticsearch.BlogEs;

@RepositoryRestResource
public interface BlogEsRepository extends ElasticsearchRepository<BlogEs, Long> {

}
