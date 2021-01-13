package com.blog.application.model;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BlogAdapter implements JsonSerializer<Blog> {
	@Override
	public JsonElement serialize(Blog blog, Type type, JsonSerializationContext jsc) {
		JsonObject messageJsonObject = new JsonObject();
		messageJsonObject.addProperty("blogId", blog.getBlogId());
		messageJsonObject.addProperty("blogTitle", blog.getBlogTitle());
		messageJsonObject.addProperty("blogDescription", blog.getBlogDescription());
		messageJsonObject.addProperty("lastUpdatedDate", blog.getLastUpdateDate().toString());
		messageJsonObject.addProperty("blogId", blog.getBlogId());
		messageJsonObject.addProperty("accountId", blog.getAccountId());
		messageJsonObject.addProperty("status", blog.getStatus());
		messageJsonObject.addProperty("blogPost", blog.getBlogPosts().toString());
		return messageJsonObject;
	}
}
