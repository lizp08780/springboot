package com.lizp.springboot.shiro;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.util.SavedRequest;

public class CustomSavedRequest extends SavedRequest {

	private static final long serialVersionUID = 5389422171155869102L;
	private String contentType;
	private String scheme;
	private String domain;
	private int port;

	/**
	 * Constructs a new instance from the given HTTP request.
	 *
	 * @param request
	 *            the current request to save.
	 */
	public CustomSavedRequest(HttpServletRequest request) {
		super(request);
		this.contentType = request.getContentType();
		this.scheme = request.getScheme();
		this.domain = request.getServerName();
		this.port = request.getServerPort();
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "CustomSavedRequest{" + "contentType='" + contentType + '\'' + ", scheme='" + scheme + '\''
				+ ", domain='" + domain + '\'' + ", port=" + port + "} " + super.toString();
	}
}
