package com.lizp.springboot.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.springframework.stereotype.Component;

import com.lizp.springboot.domain.OnlineSession;
import com.lizp.springboot.domain.SysUserOnline;

import nl.bitwalker.useragentutils.UserAgent;

@Component
public class OnlineSessionFactory implements SessionFactory {

	@Override
	public Session createSession(SessionContext initData) {
		OnlineSession session = new OnlineSession();
		if (initData != null && initData instanceof WebSessionContext) {
			WebSessionContext sessionContext = (WebSessionContext) initData;
			HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
			if (request != null) {
				UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
				// 获取客户端操作系统
				String os = userAgent.getOperatingSystem().getName();
				// 获取客户端浏览器
				String browser = userAgent.getBrowser().getName();
				session.setHost(IpUtils.getIp(request));
				session.setBrowser(browser);
				session.setOs(os);
			}
		}
		return session;
	}

	public Session createSession(SysUserOnline userOnline) {
		OnlineSession onlineSession = userOnline.getSession();
		if (onlineSession != null && onlineSession.getId() == null) {
			//onlineSession.setId(userOnline.getSessionId());
		}
		return userOnline.getSession();
	}

}
