package com.lizp.springboot.shiro;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

public class UserSessionDAO extends EnterpriseCacheSessionDAO {
	private ShiroService shiroService;

	public void setShiroService(ShiroService shiroService) {
		this.shiroService = shiroService;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable id = super.doCreate(session);
		shiroService.addSession(session);
		return id;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = super.doReadSession(sessionId);
		if (session == null) {
			session = shiroService.getSession(String.valueOf(sessionId));
		}
		return session;
	}

	@Override
	protected void doUpdate(Session session) {
		shiroService.updateSession(session);
	}

	@Override
	protected void doDelete(Session session) {
		shiroService.deleteSession(session);
	}
}
