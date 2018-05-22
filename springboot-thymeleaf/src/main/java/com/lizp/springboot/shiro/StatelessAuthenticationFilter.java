package com.lizp.springboot.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

public class StatelessAuthenticationFilter extends AccessControlFilter {
	private ShiroService shiroService;

	public StatelessAuthenticationFilter(ShiroService shiroService) {
		this.shiroService = shiroService;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		try {
			// 由业务决定如何获取token
			StatelessToken token = shiroService.getToken((HttpServletRequest) request);
			// 委托给Realm进行登录
			Subject subject = getSubject(request, response);
			subject.login(token);
			// 由业务决定如何更新token
			shiroService.refreshToken(token, (HttpServletRequest) request, (HttpServletResponse) response);
		} catch (Exception e) {
			// 登录失败
			onLoginFail(response, e);
			return false;
		}
		return true;
	}

	private void onLoginFail(ServletResponse response, Exception e) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// 登录失败时默认返回401状态码
		httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		httpResponse.getWriter()
				.write("login error:\n" + StringUtils.join(ExceptionUtils.getRootCauseStackTrace(e), "\n"));
	}
}
