package com.lizp.springboot.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.lizp.springboot.annotation.Log;
import com.lizp.springboot.domain.SysLog;
import com.lizp.springboot.persist.SysLogMapper;
import com.lizp.springboot.util.IpUtils;

@Aspect
@Component
public class LogAspect {
	@Autowired
	private SysLogMapper logMapper;

	@Pointcut("@annotation(com.lizp.springboot.annotation.Log)")
	private void pointcut() {
	}

	@After("pointcut()")
	public void insertLogSuccess(JoinPoint jp) {
		addLog(jp, getDesc(jp));
	}

	@AfterThrowing(value = "pointcut()", throwing = "e")
	public void afterException(JoinPoint joinPoint, Exception e) {
		System.out.print("-----------afterException:" + e.getMessage());
		addLog(joinPoint, getDesc(joinPoint) + e.getMessage());
	}

	private void addLog(JoinPoint jp, String text) {
		Log.LOG_TYPE type = getType(jp);
		SysLog log = new SysLog();
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String ip = IpUtils.getIp(request);
			log.setIp(ip);
		}
		log.setCreateTime(new Date());
		log.setType(type.toString());
		log.setText(text);

		Object[] obj = jp.getArgs();
		StringBuffer buffer = new StringBuffer();
		if (obj != null) {
			for (int i = 0; i < obj.length; i++) {
				Object o = obj[i];
				if (o instanceof Model) {
					continue;
				}
				String parameter = null;
				try {
					parameter = JSON.toJSONString(o);
				} catch (Exception e) {
					continue;
				}
				buffer.append("[参数" + (i + 1) + ":");
				buffer.append(parameter);
				buffer.append("]");
			}
		}
		log.setParam(buffer.toString());
		log.setUserName("test");
		logMapper.insert(log);
	}

	private String getDesc(JoinPoint joinPoint) {
		MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
		Method method = methodName.getMethod();
		return method.getAnnotation(Log.class).desc();
	}

	private Log.LOG_TYPE getType(JoinPoint joinPoint) {
		MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
		Method method = methodName.getMethod();
		return method.getAnnotation(Log.class).type();
	}
}
