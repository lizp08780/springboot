package com.lizp.springboot.util;

import java.util.HashMap;

public class Message extends HashMap<String, Object> {

	private static final long serialVersionUID = 1236534184884857030L;

	public static Message error() {
		return error(1, "操作失败");
	}

	public static Message error(String msg) {
		return error(500, msg);
	}

	public static Message error(int code, String msg) {
		Message json = new Message();
		json.put("code", code);
		json.put("msg", msg);
		return json;
	}

	public static Message success(String msg) {
		Message json = new Message();
		json.put("msg", msg);
		json.put("code", 0);
		return json;
	}

	public static Message success() {
		return Message.success("操作成功");
	}

	@Override
	public Message put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
