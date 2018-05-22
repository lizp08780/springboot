package com.lizp.springboot.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

public class UserSessionFactory implements SessionFactory {
	@Override
	public Session createSession(SessionContext initData) {
		if (initData != null) {
			String host = initData.getHost();
			if (host != null) {
				return new UserSession(host);
			}
		}
		return new UserSession();
	}
}
