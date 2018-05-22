package com.lizp.springboot.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import com.google.code.kaptcha.Constants;

public class CaptchaValidateFilter extends AccessControlFilter {
	private boolean captchaEbabled = true;
	private String captchaType = "math";

	public boolean isCaptchaEbabled() {
		return captchaEbabled;
	}

	public void setCaptchaEbabled(boolean captchaEbabled) {
		this.captchaEbabled = captchaEbabled;
	}

	public String getCaptchaType() {
		return captchaType;
	}

	public void setCaptchaType(String captchaType) {
		this.captchaType = captchaType;
	}

	// 2.
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// 验证码禁用 或不是表单提交 允许访问
		if (captchaEbabled == false || !"post".equals(httpServletRequest.getMethod().toLowerCase())) {
			return true;
		}
		// 校验验证码
		return validateResponse(httpServletRequest, httpServletRequest.getParameter("validateCode"));
	}

	// 3.
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		request.setAttribute("captcha", "验证码错误");
		return true;
	}

	// 1.
	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		request.setAttribute("captchaEbabled", captchaEbabled);
		request.setAttribute("captchaType", captchaType);
		return super.onPreHandle(request, response, mappedValue);
	}

	private boolean validateResponse(HttpServletRequest request, String validateCode) {
		Object obj = SecurityUtils.getSubject().getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		String code = String.valueOf(obj != null ? obj : "");
		if (StringUtils.isBlank(validateCode) || !validateCode.equalsIgnoreCase(code)) {
			return false;
		}
		return true;
	}

}
