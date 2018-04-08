package com.lizp.springboot.message;

public class DefaultMessage implements Message {
	private static final long serialVersionUID = -817797759599318795L;
	private final byte[] channel;
	private final byte[] body;
	private String toString;

	public DefaultMessage(byte[] channel, byte[] body) {
		this.channel = channel;
		this.body = body;
	}

	@Override
	public byte[] getBody() {
		return body != null ? body.clone() : null;
	}

	@Override
	public byte[] getChannel() {
		return channel != null ? channel.clone() : null;
	}

	public String toString() {
		if (toString == null) {
			toString = new String(body.toString());
		}
		return toString;
	}
}
