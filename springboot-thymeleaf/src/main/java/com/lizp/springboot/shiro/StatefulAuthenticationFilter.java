package com.lizp.springboot.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import com.lizp.springboot.util.HttpUtils;

public class StatefulAuthenticationFilter extends PassThruAuthenticationFilter {
	@Override
	protected void saveRequest(ServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		SavedRequest savedRequest = new CustomSavedRequest(httpRequest);
		session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
	}

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		String loginUrl = getLoginUrl();
		try {
			HttpServletRequest httpRequest = WebUtils.toHttp(request);
			String suffix = HttpUtils.getSuffix(httpRequest.getRequestURI());
			MediaType mediaType;
			if (StringUtils.equalsIgnoreCase(suffix, "json")) {
				mediaType = MediaType.APPLICATION_JSON;
			} else if (StringUtils.equalsIgnoreCase(suffix, "xml")) {
				mediaType = MediaType.APPLICATION_XML;
			} else {
				mediaType = MediaType.parseMediaType(request.getContentType());
			}
			// 若是ajax请求，则转向返回状态的地址
			if (mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)
					|| mediaType.isCompatibleWith(MediaType.APPLICATION_XML)) {
				String suffix1 = HttpUtils.getSuffix(loginUrl);
				loginUrl = StringUtils.substringBeforeLast(loginUrl, "." + suffix1) + "_status." + suffix;
				// 避免回到json页面
				SecurityUtils.getSubject().getSession().removeAttribute(WebUtils.SAVED_REQUEST_KEY);
			}
		} catch (Exception e) {

		}

		WebUtils.issueRedirect(request, response, loginUrl);
	}

	@Override
	public String getLoginUrl() {
		return super.getLoginUrl();
	}
}
