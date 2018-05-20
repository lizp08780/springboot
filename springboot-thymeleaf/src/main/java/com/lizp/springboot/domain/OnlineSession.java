package com.lizp.springboot.domain;

import org.apache.shiro.session.mgt.SimpleSession;

public class OnlineSession extends SimpleSession {

	private static final long serialVersionUID = 7599665883408814270L;
	/** 用户ID */
	private Long userId;

	/** 用户名称 */
	private String loginName;

	/** 部门名称 */
	private String deptName;

	/** 登录IP地址 */
	private String host;

	/** 浏览器类型 */
	private String browser;

	/** 操作系统 */
	private String os;

	/** 在线状态 */
	private OnlineStatus status = OnlineStatus.on_line;

	/** 属性是否改变 优化session数据同步 */
	private transient boolean attributeChanged = false;

	public static enum OnlineStatus {
		/** 用户状态 */
		on_line("在线"), off_line("离线");
		private final String info;

		private OnlineStatus(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public OnlineStatus getStatus() {
		return status;
	}

	public void setStatus(OnlineStatus status) {
		this.status = status;
	}

	public boolean isAttributeChanged() {
		return attributeChanged;
	}

	public void setAttributeChanged(boolean attributeChanged) {
		this.attributeChanged = attributeChanged;
	}

}
