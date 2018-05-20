package com.lizp.springboot.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lizp.springboot.domain.SysMenu;
import com.lizp.springboot.domain.SysRole;
import com.lizp.springboot.domain.SysUser;
import com.lizp.springboot.persist.SysMenuMapper;
import com.lizp.springboot.persist.SysRoleMapper;
import com.lizp.springboot.persist.SysUserMapper;

public class UserRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;

	/**
	 * 此方法调用 hasRole,hasPermission的时候才会进行回调. <br>
	 * 1、如果用户正常退出，缓存自动清空；<br>
	 * 2、如果用户非正常退出，缓存自动清空； <br>
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。<br>
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		Integer userId = user.getUserId();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 角色加入AuthorizationInfo认证对象
		info.setRoles(selectRoleKeys(userId));
		// 权限加入AuthorizationInfo认证对象
		info.setStringPermissions(selectPermsByUserId(userId));
		return info;
	}

	private Set<String> selectRoleKeys(Integer userId) {
		List<SysRole> perms = sysRoleMapper.selectAll();
		Set<String> permsSet = new HashSet<>();
		for (SysRole perm : perms) {
			permsSet.add(perm.getRoleKey());
		}
		return permsSet;
	}

	private Set<String> selectPermsByUserId(Integer userId) {
		List<SysMenu> perms = sysMenuMapper.selectAll();
		Set<String> permsSet = new HashSet<>();
		for (SysMenu perm : perms) {
			permsSet.add(perm.getPerms());
		}
		return permsSet;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername();
		String password = "";
		if (upToken.getPassword() != null) {
			password = new String(upToken.getPassword());
		}

		SysUser user = new SysUser();
		try {
			user.setLoginName(username);
			user = sysUserMapper.selectOne(user);
		} catch (Exception e) {
			log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
			throw new AuthenticationException(e.getMessage(), e);
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}

}
