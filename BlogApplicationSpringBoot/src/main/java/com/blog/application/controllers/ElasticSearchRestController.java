package com.blog.application.controllers;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.blog.application.model.elasticsearch.BlogEs;
import com.blog.application.service.IAccountService;
import com.blog.application.utils.Constants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class sets up the controller for the Account REST end points
 */
//@RestController
public class ElasticSearchRestController {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchRestController.class);

	/** The account service. */
	@Autowired
	IAccountService accountService;

	@Autowired
	Client client;

	/**
	 * Gets the blog by Id
	 *
	 * @return the blog
	 */
	@GetMapping(value = "/blogEs/{id}", produces = "application/json")
	@ApiOperation(value = "View a blog By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the blog"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public Map<String, Object> getBlogById(@PathVariable final String id) {
		GetResponse getResponse = client.prepareGet(Constants.BLOGS, Constants.BLOG, id).get();
		return getResponse.getSource();
	}

	@GetMapping("/blogEs/name/{field}")
	public Map<String, Object> searchByName(@PathVariable final String field) {
		Map<String, Object> map = null;
		SearchResponse response = client.prepareSearch(Constants.BLOGS).setTypes(Constants.BLOG)
				.setSearchType(SearchType.QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchQuery(Constants.BLOG_DESCRIPTION, field)).get();
		List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
		map = searchHits.get(0).getSourceAsMap();
		return map;
	}

	@PutMapping("/blogEs/update/{id}")
	public String update(@PathVariable final String id) throws IOException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(Constants.BLOGS).type(Constants.BLOG).id(id)
				.doc(jsonBuilder().startObject().field(Constants.BLOG_DESCRIPTION, "test").endObject());
		try {
			UpdateResponse updateResponse = client.update(updateRequest).get();
			LOGGER.info("updateResponse.status(): {}", updateResponse.status());
			return updateResponse.status().toString();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.warn("Interrupted!", e);
			// Restore interrupted state...
			Thread.currentThread().interrupt();
		}
		return "Exception";
	}

	@GetMapping("/blogEs//delete/{id}")
	public String delete(@PathVariable final String id) {
		DeleteResponse deleteResponse = client.prepareDelete(Constants.BLOGS, Constants.BLOG, id).get();
		return deleteResponse.getResult().toString();
	}

	@PostMapping("/create")
	public String create(@RequestBody BlogEs blog) throws IOException {
		IndexResponse response = client.prepareIndex(Constants.BLOGS, Constants.BLOG, String.valueOf(blog.getBlogId()))
				.setSource(jsonBuilder().startObject().field(Constants.BLOG_TITLE, blog.getBlogTitle())
						.field(Constants.BLOG_DESCRIPTION, blog.getBlogDescription()).endObject())
				.get();
		LOGGER.info("response.getId(): {}", response.getId());
		return response.getResult().toString();
	}
}