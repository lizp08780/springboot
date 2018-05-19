package com.lizp.springboot.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtils {
	/**
	 * 获取getStrAttribute
	 */
	public static String getStrAttribute(String name) {
		return valueAsStr(getRequestAttributes().getRequest().getAttribute(name));
	}

	/**
	 * 获取getIntAttribute
	 */
	public static int getIntAttribute(String name) {
		return valueAsInt(getRequestAttributes().getRequest().getAttribute(name));
	}

	/**
	 * 获取getStrParameter
	 */
	public static String getStrParameter(String name) {
		return valueAsStr(getRequestAttributes().getRequest().getParameter(name));
	}

	/**
	 * 获取getIntParameter
	 */
	public static Integer getIntParameter(String name) {
		return valueAsInt(getRequestAttributes().getRequest().getParameter(name));
	}

	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}

	/**
	 * 获取request
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}

	/**
	 * 获取response
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}

	/**
	 * 获取session
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 是否是Ajax异步请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {

		String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1) {
			return true;
		}

		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
			return true;
		}

		String uri = request.getRequestURI();
		if (StringUtils.equalsAnyIgnoreCase(uri, ".json", ".xml")) {
			return true;
		}

		String ajax = request.getParameter("__ajax");
		if (StringUtils.equalsAnyIgnoreCase(ajax, "json", "xml")) {
			return true;
		}
		return false;
	}

	public static Integer valueAsInt(Object value) {
		if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Number) {
			return ((Number) value).intValue();
		} else if (value instanceof String) {
			if ("NaN".equals(value)) {
				return null;
			}
			return Integer.valueOf((String) value);
		} else if (value instanceof Boolean) {
			return ((Boolean) value) ? 1 : 0;
		} else {
			return null;
		}
	}

	public static String valueAsStr(Object value) {
		if (value instanceof String) {
			return (String) value;
		} else if (value != null) {
			return value.toString();
		} else {
			return null;
		}
	}
}
