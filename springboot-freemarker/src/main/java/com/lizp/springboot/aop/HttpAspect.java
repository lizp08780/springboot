package com.lizp.springboot.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component // 将这个类引入spring容器中去
public class HttpAspect {
	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

	@Pointcut("execution(public * com.lizp.springboot.controller..*(..))")
	public void cut() {
	}

	@Before("cut()") // 在调用上面 @Pointcut标注的方法前执行以下方法
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		logger.info("url={}", request.getRequestURI());
		logger.info("method={}", request.getMethod());
		logger.info("ip={}", request.getRemoteAddr());
		logger.info("class_method={}",
				joinPoint.getSignature().getDeclaringTypeName() + '.' + joinPoint.getSignature().getName());
		logger.info("args={}", ArrayUtils.toString(joinPoint.getArgs()));
	}

	@After("cut()") // 无论Controller中调用方法以何种方式结束，都会执行
	public void doAfter() {
		logger.info("----doAfter-----------");
	}

	@AfterReturning(returning = "obj", pointcut = "cut()") // 在调用上面 @Pointcut标注的方法后执行。用于获取返回值
	public void doAfterReturning(Object obj) {
		logger.info("response={}", obj);
	}
}
