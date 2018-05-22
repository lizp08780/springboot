package com.lizp.springboot.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lizp.springboot.domain.SysMenu;
import com.lizp.springboot.domain.SysRole;
import com.lizp.springboot.domain.SysUser;
import com.lizp.springboot.persist.SysMenuMapper;
import com.lizp.springboot.persist.SysRoleMapper;
import com.lizp.springboot.persist.SysUserMapper;

@Component("shiroService")
public class ShiroServiceBean implements ShiroService {
	/**
	 * 最大同步间隔
	 */
	protected static final long MAX_SYNC_INTERVAL = 5 * 60 * 1000;
	// @Autowired
	// private SessionFactory userSessionFactory;
	// @Autowired
	// private StaffService staffService;
	// @Autowired
	// private PrivilegeService privilegeService;
	// @Autowired
	// private SessionService sessionService;
	// @Autowired
	// private Gson gson;
	/**
	 * 获取调用客户端的服务
	 */
	// @Autowired
	// private ApiClientService clientService;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysUserMapper sysUserMapper;

	@Override
	public Set<String> findStringRoles(String username) {
		List<SysRole> perms = sysRoleMapper.selectAll();
		Set<String> permsSet = new HashSet<>();
		for (SysRole perm : perms) {
			permsSet.add(perm.getRoleKey());
		}
		return permsSet;
	}

	@Override
	public Set<String> findStringPermissions(String username) {
		List<SysMenu> perms = sysMenuMapper.selectAll();
		Set<String> permsSet = new HashSet<>();
		for (SysMenu perm : perms) {
			permsSet.add(perm.getPerms());
		}
		return permsSet;
	}

	@Override
	public User findUser(String username) {
		SysUser sysUser = new SysUser();
		sysUser.setLoginName(username);
		sysUser = sysUserMapper.selectOne(sysUser);
		if (sysUser == null) {
			return null;
		}
		User user = new User();
		user.setUsername(sysUser.getLoginName());
		user.setPassword(sysUser.getPassword());
		user.setSalt(sysUser.getSalt());
		user.setLocked(false);
		return user;
	}

	@Override
	public boolean validatePassword(String inputPassword, byte[] salt, String storedPassword) {
		return true;
		// return PasswordUtils.validatePassword(inputPassword, salt, storedPassword);
	}

	@Override
	public void addSession(Session session) {
		if (session instanceof UserSession) {
			((UserSession) session).setAttributeChanged(true);
		}
		saveSession(session);
	}

	@Override
	public void updateSession(Session session) {
		saveSession(session);
	}

	@Override
	public void deleteSession(Session session) {

	}

	@Override
	public Session getSession(String sessionId) {
		return null;
	}

	protected void saveSession(Session session) {

	}

	@Override
	public StatelessToken getToken(HttpServletRequest request) {
		return null;
	}

	@Override
	public boolean validateToken(StatelessToken token) {
		return true;
	}

	@Override
	public void refreshToken(StatelessToken token, HttpServletRequest request, HttpServletResponse response) {

	}

}
