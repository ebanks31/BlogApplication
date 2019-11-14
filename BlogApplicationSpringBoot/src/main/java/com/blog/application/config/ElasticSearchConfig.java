package com.blog.application.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * This class keeps track of the Elastic Search configurations.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.blog.application.elasticsearch.repositories")
public class ElasticSearchConfig {
	private final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchConfig.class);

	/** The es host. */
	@Value("${elasticsearch.host}")
	private String esHost;

	/** The es port. */
	@Value("${elasticsearch.port}")
	private int esPort;

	/** The es cluster name. */
	@Value("${elasticsearch.clustername}")
	private String esClusterName;

	/**
	 * Client.
	 *
	 * @return the client
	 */
	@SuppressWarnings("resource")
	@Bean
	public Client client() {
		TransportClient client = null;
		LOGGER.info("esHost: {}", esHost);
		LOGGER.info("esPort: {}", esPort);
		LOGGER.info("esClusterName: {}", esClusterName);

		try {
			Settings settings = Settings.builder().put("cluster.name", esClusterName).build();
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return client;
	}

	/**
	 * Gets the es host.
	 *
	 * @return the es host
	 */
	public String getEsHost() {
		return esHost;
	}

	/**
	 * Sets the es host.
	 *
	 * @param esHost the new es host
	 */
	public void setEsHost(String esHost) {
		this.esHost = esHost;
	}

	/**
	 * Gets the es port.
	 *
	 * @return the es port
	 */
	public int getEsPort() {
		return esPort;
	}

	/**
	 * Sets the es port.
	 *
	 * @param esPort the new es port
	 */
	public void setEsPort(int esPort) {
		this.esPort = esPort;
	}

	/**
	 * Gets the es cluster name.
	 *
	 * @return the es cluster name
	 */
	public String getEsClusterName() {
		return esClusterName;
	}

	/**
	 * Sets the es cluster name.
	 *
	 * @param esClusterName the new es cluster name
	 */
	public void setEsClusterName(String esClusterName) {
		this.esClusterName = esClusterName;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ElasticSearchConfig [esHost=" + esHost + ", esPort=" + esPort + ", esClusterName=" + esClusterName
				+ "]";
	}
}