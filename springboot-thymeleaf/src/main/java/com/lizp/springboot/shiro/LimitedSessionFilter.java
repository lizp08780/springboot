package com.lizp.springboot.shiro;

import java.io.Serializable;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.InitializingBean;

public class LimitedSessionFilter extends AccessControlFilter implements InitializingBean {
	/**
	 * 踢出最后登录的
	 */
	private boolean kickOutLast = false;
	/**
	 * 同一个帐号最大会话数 默认1
	 */
	private int maxSession = 1;

	private SessionManager sessionManager;
	private CacheManager cacheManager;
	private Cache<String, Deque<Serializable>> cache;
	private String cacheName;

	public void setKickOutLast(boolean kickOutLast) {
		this.kickOutLast = kickOutLast;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.cache = cacheManager.getCache(cacheName);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			// 如果没有登录，直接进行之后的流程
			return true;
		}

		Session session = subject.getSession();
		String username = (String) subject.getPrincipal();
		Serializable sessionId = session.getId();

		Deque<Serializable> deque = cache.get(username);
		if (deque == null) {
			deque = new ConcurrentLinkedDeque<Serializable>();
			cache.put(username, deque);
		}

		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId) && session
				.getAttribute(ShiroConstants.SESSION_OFFLINE_TYPE) != ShiroConstants.SessionOfflineType.KICKOUT) {
			deque.push(sessionId);
		}

		// 如果队列里的sessionId数超出最大会话数，开始踢人
		while (deque.size() > maxSession) {
			Serializable kickoutSessionId;
			// 如果踢出后者
			if (kickOutLast) {
				// 最后登录的在队列前面
				kickoutSessionId = deque.removeFirst();
			} else { // 否则踢出前者
				kickoutSessionId = deque.removeLast();
			}
			try {
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null) {
					// 设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute(ShiroConstants.SESSION_OFFLINE_TYPE,
							ShiroConstants.SessionOfflineType.KICKOUT);
				}
			} catch (Exception e) {
				// ignore exception
			}
		}

		return true;
	}
}
