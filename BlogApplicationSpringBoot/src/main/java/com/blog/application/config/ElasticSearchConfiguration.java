package com.blog.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;

//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.blog.application.elasticsearch.repositories")
public class ElasticSearchConfiguration extends AbstractFactoryBean {

	private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchConfiguration.class);

	// @Value("${spring.data.elasticsearch.cluster-nodes}")
	// private String clusterNodes;

	// @Value("${spring.data.elasticsearch.cluster-name}")
	// private String clusterName;

	/** The es host. */
	@Value("${elasticsearch.host}")
	private String esHost;

	/** The es port. */
	@Value("${elasticsearch.port}")
	private int esPort;

	/** The es cluster name. */
	@Value("${elasticsearch.clustername}")
	private String esClusterName;
//	private RestHighLevelClient restHighLevelClient;

	@Override
	public void destroy() {
		/*
		 * try { if (restHighLevelClient != null) { restHighLevelClient.close(); } }
		 * catch (final Exception e) { LOG.error("Error closing ElasticSearch client: ",
		 * e); }
		 */
	}
	/*
	 * @Override public Class<RestHighLevelClient> getObjectType() { return
	 * RestHighLevelClient.class; }
	 * 
	 * @Override public boolean isSingleton() { return false; }
	 * 
	 * @Override public RestHighLevelClient createInstance() { return buildClient();
	 * }
	 * 
	 * private RestHighLevelClient buildClient() { try { restHighLevelClient = new
	 * RestHighLevelClient(RestClient.builder(new HttpHost(esHost, esPort,
	 * "http"))); } catch (Exception e) { LOG.error(e.getMessage()); } return
	 * restHighLevelClient; }
	 */

	@Override
	public Class getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object createInstance() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}