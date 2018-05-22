package com.lizp.springboot.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class UserRealm extends AuthorizingRealm {
	private static final String OR_OPERATOR = " or ";
	private static final String AND_OPERATOR = " and ";
	private static final String NOT_OPERATOR = "not ";
	private ShiroService shiroService;

	public UserRealm(CacheManager cacheManager) {
		super(cacheManager);
	}

	public UserRealm(CredentialsMatcher matcher) {
		super(matcher);
	}

	public UserRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
		super(cacheManager, matcher);
	}

	public void setShiroService(ShiroService shiroService) {
		this.shiroService = shiroService;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken || token instanceof StatelessToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 多Realm时返回任意一个
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(shiroService.findStringRoles(username));
		authorizationInfo.setStringPermissions(shiroService.findStringPermissions(username));
		return authorizationInfo;
	}

	/**
	 * 支持or and not 关键词 不支持and or混用
	 *
	 * @param principals
	 * @param permission
	 * @return
	 */
	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		if (permission.contains(OR_OPERATOR)) {
			String[] permissions = permission.split(OR_OPERATOR);
			for (String orPermission : permissions) {
				if (isPermittedWithNotOperator(principals, orPermission)) {
					return true;
				}
			}
			return false;
		} else if (permission.contains(AND_OPERATOR)) {
			String[] permissions = permission.split(AND_OPERATOR);
			for (String orPermission : permissions) {
				if (!isPermittedWithNotOperator(principals, orPermission)) {
					return false;
				}
			}
			return true;
		} else {
			return isPermittedWithNotOperator(principals, permission);
		}
	}

	private boolean isPermittedWithNotOperator(PrincipalCollection principals, String permission) {
		if (permission.startsWith(NOT_OPERATOR)) {
			return !super.isPermitted(principals, permission.substring(NOT_OPERATOR.length()));
		} else {
			return super.isPermitted(principals, permission);
		}
	}

	/**
	 * 1、如果Realm 是AuthenticatingRealm 子类，则提供给AuthenticatingRealm 内部使用的
	 * CredentialsMatcher进行凭据验证；（如果没有继承它需要在自己的Realm中自己实现验证）；
	 * 2、提供给SecurityManager来创建Subject（提供身份信息）；
	 *
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if (token instanceof UsernamePasswordToken) {
			UsernamePasswordToken upToken = (UsernamePasswordToken) token;
			String username = upToken.getUsername().trim();

			/**
			 * AuthorizingRealm调用doGetAuthenticationInfo后，才进行密码验证 当有错误时会抛出异常
			 * 未知用户：{@link org.apache.shiro.authc.UnknownAccountException}
			 * 错误超过次数：{@link org.apache.shiro.authc.ExcessiveAttemptsException}
			 * 用户锁定：{@link org.apache.shiro.authc.LockedAccountException}
			 * 密码错误：{@link org.apache.shiro.authc.IncorrectCredentialsException}
			 * 其他错误：{@link org.apache.shiro.authc.AuthenticationException} P191分布式
			 */

			ByteSource credentialsSalt = null;
			// 需要考虑缓存?
			User user = shiroService.findUser(username);

			if (user == null) {
				throw new UnknownAccountException();
			} else if (user.isLocked()) {
				throw new LockedAccountException();
			}

			if (StringUtils.isNotEmpty(user.getSalt())) {
				// credentialsSalt = new CustomByteSource(Hex.decode(user.getSalt()));
			}

			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, user.getPassword(), credentialsSalt,
					getName());
			return info;
		} else {// StatelessToken
			return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
		}
	}

	@Override
	protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
			throws AuthenticationException {
		super.assertCredentialsMatch(token, info);
		// 密码验证成功后，说明调用了登录操作，并且登录成功了
		if (token instanceof StatelessToken) {
			// 由于无状态的会话，没有登出操作，所以不会清理缓存，所以需要再下次手动登录的时候清理缓存
			// 如果需要清空缓存，则清空缓存
			if (((StatelessToken) token).isClearCache()) {
				SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
				principalCollection.add(token.getPrincipal(), getName());
				clearCachedCache(principalCollection);
			}
		}
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	public void clearCachedCache(PrincipalCollection principals) {
		clearCachedAuthenticationInfo(principals);
		clearCachedAuthorizationInfo(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}
