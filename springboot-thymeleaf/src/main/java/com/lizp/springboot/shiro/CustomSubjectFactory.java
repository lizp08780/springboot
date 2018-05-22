package com.lizp.springboot.shiro;

import javax.servlet.ServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

public class CustomSubjectFactory extends DefaultWebSubjectFactory {
	@Override
	public Subject createSubject(SubjectContext context) {
		// 首次通过SecurityUtils.getSubject()获取subject的时候token还未存在，只有Request/Response信息
		// 当调用subject.login的时候会重新建立真正的subject，但此subject中没有Request/Response信息
		if (context.getAuthenticationToken() != null) {
			// 无状态的时候不创建session
			if (context.getAuthenticationToken() instanceof StatelessToken) {
				context.setSessionCreationEnabled(false);
				// 获取SecurityUtils.getSubject()得到的subject
				Subject subject = context.getSubject();
				ServletRequest request = ((WebDelegatingSubject) subject).getServletRequest();
				// 设为不创建session
				request.setAttribute(DefaultSubjectContext.SESSION_CREATION_ENABLED, false);
			} else {
				context.setSessionCreationEnabled(true);
			}
		}
		return super.createSubject(context);
	}
}
