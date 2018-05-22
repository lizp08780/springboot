package com.lizp.springboot.shiro;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
	private ShiroService shiroService;

	public CustomCredentialsMatcher(ShiroService shiroService) {
		this.shiroService = shiroService;
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		if (authcToken instanceof org.apache.shiro.authc.UsernamePasswordToken) {
			org.apache.shiro.authc.UsernamePasswordToken token = (org.apache.shiro.authc.UsernamePasswordToken) authcToken;
			// 数据库密码
			Object accountCredentials = getCredentials(info);
			byte[] salt = null;
			if (info instanceof SimpleAuthenticationInfo) {
				//salt = ((SimpleAuthenticationInfo) info).getCredentialsSalt().getBytes();
			}
			if (token instanceof UsernamePasswordToken) {
				// 微信和第三方登录自动登录
				if (((UsernamePasswordToken) token).isThirdParty()) {
					return true;
				}
			}
			// 将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
			return shiroService.validatePassword(String.valueOf(token.getPassword()), salt, accountCredentials + "");
		} else if (authcToken instanceof StatelessToken) {
			return shiroService.validateToken((StatelessToken) authcToken);
		} else {
			throw new NotImplementedException("Not Implemented AuthenticationToken Credentials Match of " + authcToken);
		}
	}
}
