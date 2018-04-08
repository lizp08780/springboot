package com.lizp.springboot.message;

import org.springframework.util.Assert;

import redis.clients.jedis.BinaryJedisPubSub;

public class JedisMessageListener extends BinaryJedisPubSub {
	private final MessageListener listener;

	JedisMessageListener(MessageListener listener) {
		Assert.notNull(listener, "message listener is required");
		this.listener = listener;
	}

	@Override
	public void onMessage(byte[] channel, byte[] message) {
		listener.onMessage(new DefaultMessage(channel, message));
	}

}
