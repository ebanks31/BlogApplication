package com.blog.application.service;

/**
 * Defines the application email-sending contract.
 *
 * <p>Implementations provide a small abstraction over the configured mail
 * provider so callers do not depend directly on the mail client API.</p>
 */
public interface IEmailService {
	void sendSimpleMessage(String to, String subject, String text);
}
