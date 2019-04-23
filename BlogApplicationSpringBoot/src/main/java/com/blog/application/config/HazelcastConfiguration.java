package com.blog.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;

/**
 * This class sets up the configuration for HazelCast Configuration.
 */
@Configuration
@Profile("!test")
public class HazelcastConfiguration {

	/**
	 * Hazel cast config.
	 *
	 * @return the config
	 */
	@Profile("prod")
	@Bean
	public Config hazelCastConfigProd() {
		Config config = new Config();
		config.setInstanceName("hazelcast-instance")
				.addMapConfig(new MapConfig().setName("configuration")
						.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
						.setEvictionPolicy(EvictionPolicy.LRU).setTimeToLiveSeconds(-1));
		return config;
	}

	/**
	 * Hazel cast config.
	 *
	 * @return the config
	 */
	@Profile("dev")
	@Bean
	public Config hazelCastConfigDev() {
		Config config = new Config();
		config.setInstanceName("hazelcast-instance")
				.addMapConfig(new MapConfig().setName("configuration")
						.setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
						.setEvictionPolicy(EvictionPolicy.LRU).setTimeToLiveSeconds(-1));
		return config;
	}
}