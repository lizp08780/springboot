package com.lizp.springboot.shiro;

import org.apache.shiro.session.mgt.SimpleSession;

public class UserSession extends SimpleSession {

	private static final long serialVersionUID = -6773722369144325046L;
	/**
	 * 属性是否改变 优化session数据同步
	 */
	private transient boolean attributeChanged = false;

	public UserSession() {

	}

	public UserSession(String host) {
		super(host);
	}

	public boolean isAttributeChanged() {
		return attributeChanged;
	}

	/**
	 * 设置属性是否变更，由应用程序自行设置
	 *
	 * @param attributeChanged
	 */
	public void setAttributeChanged(boolean attributeChanged) {
		this.attributeChanged = attributeChanged;
	}
}
