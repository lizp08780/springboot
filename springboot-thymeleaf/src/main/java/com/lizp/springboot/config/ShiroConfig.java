package com.lizp.springboot.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lizp.springboot.filter.CaptchaValidateFilter;
import com.lizp.springboot.filter.LogoutFilter;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	private String loginUrl;
	private String unauthorizedUrl;
	private boolean captchaEbabled = true;
	private String captchaType = "math";
	private String domain;
	private String path;
	private boolean httpOnly;
	private int maxAge = 1;
	// private int expireTime;

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// Shiro的核心安全接口,这个属性是必须的
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 身份认证失败，则跳转到登录页面的配置
		shiroFilterFactoryBean.setLoginUrl(loginUrl);
		// 权限认证失败，则跳转到指定页面
		shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
		// Shiro连接约束配置，即过滤链的定义
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 对静态资源设置匿名访问
		filterChainDefinitionMap.put("/favicon.ico**", "anon");
		filterChainDefinitionMap.put("/lizp.png**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/docs/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/ajax/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/lizp/**", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
		// 退出 logout地址，shiro去清除session
		filterChainDefinitionMap.put("/logout", "logout");
		// 不需要拦截的访问
		filterChainDefinitionMap.put("/login", "anon,captchaValidate");
		// 系统权限列表
		// filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());

		Map<String, Filter> filters = new LinkedHashMap<>();
		// filters.put("onlineSession", onlineSessionFilter());
		// filters.put("syncOnlineSession", syncOnlineSessionFilter());
		filters.put("captchaValidate", captchaValidateFilter());
		// 注销成功，则跳转到指定页面
		filters.put("logout", logoutFilter());
		shiroFilterFactoryBean.setFilters(filters);

		// 所有请求需要认证
		filterChainDefinitionMap.put("/**", "user");
		// 系统请求记录当前会话
		//filterChainDefinitionMap.put("/main", "onlineSession,syncOnlineSession");
		//filterChainDefinitionMap.put("/system/**", "onlineSession,syncOnlineSession");
		//filterChainDefinitionMap.put("/monitor/**", "onlineSession,syncOnlineSession");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	public LogoutFilter logoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setLoginUrl(loginUrl);
		return logoutFilter;
	}

	@Bean
	public CaptchaValidateFilter captchaValidateFilter() {
		CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
		captchaValidateFilter.setCaptchaEbabled(captchaEbabled);
		captchaValidateFilter.setCaptchaType(captchaType);
		return captchaValidateFilter;
	}

	// @Bean
	// public SyncOnlineSessionFilter syncOnlineSessionFilter() {
	// SyncOnlineSessionFilter syncOnlineSessionFilter = new
	// SyncOnlineSessionFilter();
	// return syncOnlineSessionFilter;
	// }

	/**
	 * 开启Shiro注解通知器
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 为了在thymeleaf里使用shiro的标签的bean
	 * 
	 * @return
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	/**
	 * cookie管理对象;
	 * 
	 * @return
	 */
	private CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		cookieRememberMeManager.setCipherKey(Base64.decode("fCq+/xW488hMTCD+cmJ3aQ=="));
		return cookieRememberMeManager;
	}

	/**
	 * cookie对象;
	 * 
	 * @return
	 */
	private SimpleCookie rememberMeCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(maxAge * 24 * 60 * 60);
		return cookie;
	}

	@Bean
	public SecurityManager securityManager(UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(userRealm);
		// 注入记住我管理器;
		securityManager.setRememberMeManager(rememberMeManager());
		// 注入缓存管理器;
		// securityManager.setCacheManager(getEhCacheManager());
		// session管理器
		// securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	// @Bean
	// public OnlineWebSessionManager sessionManager() {
	// OnlineWebSessionManager manager = new OnlineWebSessionManager();
	// // 加入缓存管理器
	// // manager.setCacheManager(getEhCacheManager());
	// // 删除过期的session
	// manager.setDeleteInvalidSessions(true);
	// // 设置全局session超时时间
	// manager.setGlobalSessionTimeout(expireTime * 60 * 1000);
	// // 定义要使用的无效的Session定时调度器
	// manager.setSessionValidationScheduler(sessionValidationScheduler());
	// // 是否定时检查session
	// manager.setSessionValidationSchedulerEnabled(true);
	// // 自定义SessionDao
	// manager.setSessionDAO(sessionDAO());
	// // 自定义sessionFactory
	// manager.setSessionFactory(sessionFactory());
	// return manager;
	// }

	// @Bean
	// public EhCacheManager getEhCacheManager() {
	// EhCacheManager em = new EhCacheManager();
	// em.setCacheManagerConfigFile("classpath:ehcache/ehcache-shiro.xml");
	// return em;
	// }

	/**
	 * 身份认证realm(这个需要自己写，账号密码校验；权限等)
	 * 
	 * @return
	 */
	@Bean
	public UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		// userRealm.setCacheManager(EhCacheManager cacheManager);
		// userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return userRealm;
	}

	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于md5(md5(""));
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);// 表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
		return hashedCredentialsMatcher;
	}

	// @Bean
	// public SpringSessionValidationScheduler sessionValidationScheduler() {
	// SpringSessionValidationScheduler sessionValidationScheduler = new
	// SpringSessionValidationScheduler();
	// // 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
	// sessionValidationScheduler.setSessionValidationInterval(validationInterval *
	// 60 * 1000);
	// // 设置会话验证调度器进行会话验证时的会话管理器
	// sessionValidationScheduler.setSessionManager(sessionValidationManager());
	// return sessionValidationScheduler;
	// }
}
