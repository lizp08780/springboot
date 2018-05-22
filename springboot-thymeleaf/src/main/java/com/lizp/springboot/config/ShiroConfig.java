package com.lizp.springboot.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lizp.springboot.filter.CaptchaValidateFilter;
import com.lizp.springboot.shiro.CustomCredentialsMatcher;
import com.lizp.springboot.shiro.CustomSubjectFactory;
import com.lizp.springboot.shiro.InspectSessionFilter;
import com.lizp.springboot.shiro.LimitedSessionFilter;
import com.lizp.springboot.shiro.RetryLimitHashedCredentialsMatcher;
import com.lizp.springboot.shiro.ShiroService;
import com.lizp.springboot.shiro.ShiroSessionListener;
import com.lizp.springboot.shiro.SpringCacheManagerWrapper;
import com.lizp.springboot.shiro.StatefulAuthenticationFilter;
import com.lizp.springboot.shiro.StatelessAuthenticationFilter;
import com.lizp.springboot.shiro.UserRealm;
import com.lizp.springboot.shiro.UserSessionDAO;
import com.lizp.springboot.shiro.UserSessionFactory;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	private String loginUrl = "/login";
	private String unauthorizedUrl = "/unauth";
	private boolean captchaEbabled = true;
	private String captchaType = "math";
	private String domain = "";
	private String path = "/";
	private boolean httpOnly = true;
	private int maxAge = 30;
	private int expireTime = 30;

	/**
	 * Shiro过滤器配置
	 * 
	 * @param securityManager
	 * @param logoutFilter
	 * @param captchaValidateFilter
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, ShiroService shiroService,
			SpringCacheManagerWrapper shiroCacheManager, DefaultWebSessionManager sessionManager,
			UserSessionDAO sessionDAO) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl(loginUrl);
		// 权限认证失败，则跳转到指定页面
		shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
		// Shiro连接约束配置，即过滤链的定义
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 对静态资源设置匿名访问
		filterChainDefinitionMap.put("/favicon.ico**", "anon");
		filterChainDefinitionMap.put("/lizp.png**", "anon");
		filterChainDefinitionMap.put("/ajax/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		// filterChainDefinitionMap.put("/docs/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/lizp/**", "anon");

		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/captcha/**", "anon");
		filterChainDefinitionMap.put("/logout", "anon");
		filterChainDefinitionMap.put("/login", "anon,captchaValidate");
		filterChainDefinitionMap.put("/**", "anon");
		// filterChainDefinitionMap.put("/**",
		// "authcful,limit,inspect,perms,roles");暂时不判断权限
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		// 自定义拦截器
		Map<String, Filter> filters = new LinkedHashMap<>();
		filters.put("captchaValidate", captchaValidateFilter());
		// 注销成功，则跳转到指定页面
		// filters.put("logout", logoutFilter());
		filters.put("authcful", new StatefulAuthenticationFilter());
		filters.put("authcless", new StatelessAuthenticationFilter(shiroService));
		filters.put("limit", limitSessionFilter(shiroCacheManager, sessionManager));
		filters.put("inspect", inspectSessionFilter(sessionDAO));
		shiroFilterFactoryBean.setFilters(filters);

		return shiroFilterFactoryBean;
	}

	/**
	 * 开启Shiro注解通知器
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
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

	/**
	 * 开启Shiro代理
	 * 
	 * @return
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	/**
	 * 安全管理器
	 * 
	 * @param userRealm
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(UserRealm userRealm, SpringCacheManagerWrapper shiroCacheManager,
			CookieRememberMeManager rememberMeManager, CustomSubjectFactory subjectFactory,
			DefaultWebSessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		securityManager.setSubjectFactory(subjectFactory);
		securityManager.setSessionManager(sessionManager);
		securityManager.setRememberMeManager(rememberMeManager);
		// securityManager.setCacheManager(shiroCacheManager);
		return securityManager;
	}

	@Bean
	public ShiroSessionListener userSessionListener() {
		return new ShiroSessionListener();
	}

	@Bean
	public CustomSubjectFactory subjectFactory() {
		CustomSubjectFactory subjectFactory = new CustomSubjectFactory();
		return subjectFactory;
	}

	@Bean
	public DefaultWebSessionManager sessionManager(SpringCacheManagerWrapper shiroCacheManager,
			UserSessionFactory sessionFactory, SimpleCookie sessionIdCookie, ShiroSessionListener userSessionListener,
			UserSessionDAO sessionDAO) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionFactory(sessionFactory);
		Collection<SessionListener> listeners = new HashSet<SessionListener>();
		listeners.add(userSessionListener);
		sessionManager.setSessionListeners(listeners);
		sessionManager.setGlobalSessionTimeout(expireTime * 60 * 1000);
		// sessionManager.setSessionDAO(sessionDAO);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationInterval(60 * 60 * 1000);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setCacheManager(shiroCacheManager);
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie);
		return sessionManager;
	}

	@Bean
	public CustomCredentialsMatcher customCredentialsMatcher(ShiroService shiroService) {
		CustomCredentialsMatcher customCredentialsMatcher = new CustomCredentialsMatcher(shiroService);
		return customCredentialsMatcher;
	}

	@Bean
	public RetryLimitHashedCredentialsMatcher credentialsMatcher(CustomCredentialsMatcher customCredentialsMatcher,
			SpringCacheManagerWrapper shiroCacheManager) {
		RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher();
		credentialsMatcher.setDelegate(customCredentialsMatcher);
		credentialsMatcher.setCacheManager(shiroCacheManager);
		credentialsMatcher.setCacheName("shiro-passwordRetryCache");
		credentialsMatcher.setRetryLimit(5);
		credentialsMatcher.setLockTime(600);
		return credentialsMatcher;
	}

	@Bean
	public UserSessionFactory sessionFactory() {
		return new UserSessionFactory();
	}

	@Bean
	public SpringCacheManagerWrapper shiroCacheManager(RedissonSpringCacheManager cacheManager) {
		SpringCacheManagerWrapper shiroCacheManager = new SpringCacheManagerWrapper();
		shiroCacheManager.setCacheManager(cacheManager);
		return shiroCacheManager;
	}

	@Bean
	public RedissonSpringCacheManager cacheManager(RedissonClient redissonClient) {
		RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient);
		Map<String, CacheConfig> configMap = new ConcurrentHashMap<String, CacheConfig>();
		CacheConfig cacheConfig = new CacheConfig(30 * 60 * 1000, 15 * 60 * 1000);
		// cacheConfig.setMaxSize(maxSize);
		configMap.put("shiroCache", cacheConfig);

		CacheConfig cacheConfig2 = new CacheConfig(30 * 60 * 1000, 15 * 60 * 1000);
		// cacheConfig.setMaxSize(maxSize);
		configMap.put("sessionCache", cacheConfig2);
		return cacheManager;
	}

	/**
	 * 身份认证realm(这个需要自己写，账号密码校验；权限等)
	 * 
	 * @return
	 */
	// @Bean
	// public UserRealm userRealm(HashedCredentialsMatcher hashedCredentialsMatcher)
	// {
	// UserRealm userRealm = new UserRealm();
	// // userRealm.setCacheManager(shiroCacheManager);无需重复设置
	// // userRealm.setCredentialsMatcher(hashedCredentialsMatcher);暂时注释
	// return userRealm;
	// }

	@Bean
	public UserRealm userRealm(SpringCacheManagerWrapper shiroCacheManager,
			RetryLimitHashedCredentialsMatcher credentialsMatcher, ShiroService shiroService) {
		UserRealm userRealm = new UserRealm(shiroCacheManager, credentialsMatcher);
		userRealm.setShiroService(shiroService);
		userRealm.setAuthorizationCacheName("shiro-authorizationCache");
		userRealm.setAuthenticationCacheName("shiro-authenticationCache");
		userRealm.setAuthenticationCachingEnabled(true);
		userRealm.setAuthorizationCachingEnabled(true);
		return userRealm;
	}

	// @Bean
	// public HashedCredentialsMatcher hashedCredentialsMatcher() {
	// HashedCredentialsMatcher hashedCredentialsMatcher = new
	// HashedCredentialsMatcher();
	// hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
	// hashedCredentialsMatcher.setHashIterations(2);//
	// 散列的次数，比如散列两次，相当于md5(md5(""));
	// hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);//
	// 表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
	// return hashedCredentialsMatcher;
	// }

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

	/**
	 * 退出过滤器
	 * 
	 * @return
	 */
	// private LogoutFilter logoutFilter() {
	// LogoutFilter logoutFilter = new LogoutFilter();
	// logoutFilter.setLoginUrl(loginUrl);
	// return logoutFilter;
	// }

	private CaptchaValidateFilter captchaValidateFilter() {
		CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
		captchaValidateFilter.setCaptchaEbabled(captchaEbabled);
		captchaValidateFilter.setCaptchaType(captchaType);
		return captchaValidateFilter;
	}

	/**
	 * cookie对象;
	 * 
	 * @return
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(maxAge * 24 * 60 * 60);
		return cookie;
	}

	@Bean
	public SimpleCookie sessionIdCookie() {
		SimpleCookie cookie = new SimpleCookie("uid");
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(-1);
		return cookie;
	}

	@Bean
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		return new JavaUuidSessionIdGenerator();
	}

	/**
	 * cookie管理对象;
	 * 
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		rememberMeManager.setCookie(rememberMeCookie);
		rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		return rememberMeManager;
	}

	@Bean
	public UserSessionDAO sessionDAO(SpringCacheManagerWrapper shiroCacheManager, ShiroService shiroService,
			JavaUuidSessionIdGenerator sessionIdGenerator) {
		UserSessionDAO sessionDAO = new UserSessionDAO();
		sessionDAO.setShiroService(shiroService);
		sessionDAO.setCacheManager(shiroCacheManager);
		sessionDAO.setSessionIdGenerator(sessionIdGenerator);
		sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		return sessionDAO;
	}

	// @Bean
	public LimitedSessionFilter limitSessionFilter(SpringCacheManagerWrapper shiroCacheManager,
			DefaultWebSessionManager sessionManager) {
		LimitedSessionFilter limitSessionFilter = new LimitedSessionFilter();
		limitSessionFilter.setCacheName("shiro-limitedSessionCache");
		limitSessionFilter.setCacheManager(shiroCacheManager);
		limitSessionFilter.setSessionManager(sessionManager);
		limitSessionFilter.setMaxSession(2);
		limitSessionFilter.setKickOutLast(false);
		return limitSessionFilter;
	}

	// @Bean
	public InspectSessionFilter inspectSessionFilter(UserSessionDAO sessionDAO) {
		InspectSessionFilter inspectSessionFilter = new InspectSessionFilter();
		inspectSessionFilter.setLogOutUrl("/login?logout");
		inspectSessionFilter.setTimeOutUrl("/login?timeout");
		inspectSessionFilter.setForceOutUrl("/login?forceout");
		inspectSessionFilter.setTimeOutUrl("/login?kickout");
		// inspectSessionFilter.setSessionDAO(sessionDAO);
		return inspectSessionFilter;
	}
}
