package com.blog.application.service;

public interface IEmailService {
	void sendSimpleMessage(String to, String subject, String text);
}
