package com.lizp.springboot.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroSessionListener implements SessionListener {
	private static final Logger log = LoggerFactory.getLogger(ShiroSessionListener.class);

	@Override
	public void onStart(Session session) {// 会话创建时触发
		log.debug("Session Created：" + session.getId());
	}

	@Override
	public void onExpiration(Session session) {// 会话过期时触发
		log.debug("Session Expired：" + session.getId());
	}

	@Override
	public void onStop(Session session) {// 退出/会话过期时触发
		log.debug("Session Stopped：" + session.getId());
	}
}
