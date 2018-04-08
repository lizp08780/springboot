package com.lizp.springboot.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;

@ConfigurationProperties(prefix = "redis")
@Component
public class RedisCacheAutoConfiguration {
	private final Logger log = LoggerFactory.getLogger(RedisCacheAutoConfiguration.class);
	private String host = "localhost";
	private int port = 6379;
	private Integer timeout = 5000;
	private String password = "";
	private int dbIndex;

	@Bean
	public JedisPool reidsPool() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		return new JedisPool(poolConfig, host, port, timeout, password, dbIndex);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public Logger getLog() {
		return log;
	}

}
