package com.blog.application.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.blog.application.controllers.BlogAdapter;
import com.blog.application.model.Blog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class Helper {
	private final static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String autosToJson(Blog blogs) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.registerTypeAdapter(Blog.class, new BlogAdapter()).create();
		return gson.toJson(blogs);
	}

	@SuppressWarnings("unchecked")
	public static JSONObject createJson(List<Blog> blogs) {
		JSONObject messageJsonObject = new JSONObject();
		JSONArray messageJsonArray = new JSONArray();
		DateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
		for (Blog blog : blogs) {
			JSONObject messageDetailsObject = new JSONObject();
			Date lastUpdatedDate = blog.getLastUpdateDate();
			messageDetailsObject.put("blogId", blog.getBlogId());
			messageDetailsObject.put("blogTitle", blog.getBlogTitle());
			messageDetailsObject.put("blogDescription", blog.getBlogDescription());
			messageDetailsObject.put("lastUpdatedDate",
					lastUpdatedDate == null ? null : dateFormat.format(blog.getLastUpdateDate()));
			messageDetailsObject.put("accountId", blog.getAccountId());
			messageDetailsObject.put("status", blog.getStatus());
			messageJsonArray.add(messageDetailsObject);
			// messageJsonObject.put("blogPost", blog.getBlogPosts());
		}
		messageJsonObject.put("blogs", messageJsonArray);
		return messageJsonObject;
	}
}