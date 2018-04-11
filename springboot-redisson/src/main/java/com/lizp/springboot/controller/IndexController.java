package com.lizp.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	@Autowired
	private RedissonClient redissonClient;

	@RequestMapping(value = "/index")
	public String index() {
		RBucket<String> str = redissonClient.getBucket("test:wo:3");
		System.err.println(str.getAndDelete());
		return "index";
	}

	public void batchSet() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("test:wo:1", "aksfs");
		map.put("test:wo:2", "cgj");
		map.put("test:wo:3", "cjccgvj");
		map.put("test:ni:1", "aksfs");
		map.put("test:ni:2", "cgj");
		map.put("test:ni:3", "cjccgvj");
		redissonClient.getBuckets().set(map);
	}

}