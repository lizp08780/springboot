package com.lizp.springboot.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lizp.springboot.domain.SysUser;

public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
	private static Logger logger = LoggerFactory.getLogger(LogoutFilter.class);
	private String loginUrl;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	@Override
	protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
		String url = loginUrl;
		if (StringUtils.isNotEmpty(url)) {
			return url;
		}
		return super.getRedirectUrl(request, response, subject);
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		try {
			Subject subject = getSubject(request, response);
			String redirectUrl = getRedirectUrl(request, response, subject);
			try {
				SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
				if (user != null) {
					// String loginName = user.getLoginName();
					// 记录用户退出日志
					// SystemLogUtils.log(loginName, CommonConstant.LOGOUT,
					// MessageUtils.message("user.logout.success"));
				}
				// 退出登录
				subject.logout();
			} catch (SessionException ise) {
				logger.error("logout fail.", ise);
			}
			issueRedirect(request, response, redirectUrl);
		} catch (Exception e) {
			logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", e);
		}
		return false;
	}
}
