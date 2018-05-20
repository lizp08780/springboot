package com.lizp.springboot.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.PathMatchingFilter;

public class SyncOnlineSessionFilter extends PathMatchingFilter {
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		//OnlineSession session = (OnlineSession) request.getAttribute("online_session");
		// 如果session stop了 也不同步
		// session停止时间，如果stopTimestamp不为null，则代表已停止
//		if (session != null && session.getUserId() != null && session.getStopTimestamp() == null) {
//			onlineSessionDAO.syncToDb(session);
//		}
		return true;
	}
}
