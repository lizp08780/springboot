package com.lizp.springboot.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class StatelessToken implements AuthenticationToken {
	private static final long serialVersionUID = 4021031325048533955L;
	private String username;
	private String digest;
	/**
	 * 是否清理缓存
	 */
	private boolean clearCache;

	public StatelessToken(String username, String digest) {
		this.username = username;
		this.digest = digest;
	}

	public StatelessToken(String username, String digest, boolean clearCache) {
		this.username = username;
		this.digest = digest;
		this.clearCache = clearCache;
	}

	public boolean isClearCache() {
		return clearCache;
	}

	public void setClearCache(boolean clearCache) {
		this.clearCache = clearCache;
	}

	@Override
	public Object getPrincipal() {
		return username;
	}

	@Override
	public Object getCredentials() {
		return digest;
	}
}
