package com.lizp.springboot.shiro;

public class ShiroConstants {
	/**
	 * 会话状态字段名
	 */
	public static final String SESSION_STATUS = "shiro_session_status";
	/**
	 * 会话字段名：退出方式
	 */
	public static final String SESSION_OFFLINE_TYPE = "shiro_session_offline_type";

	public enum SessionStatus {
		ONLINE("在线"), HIDDEN("隐身"), OFFLINE("离线");
		private final String info;

		SessionStatus(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

	public enum SessionOfflineType {
		LOGOUT("正常退出"), KICKOUT("剔除退出"), FORCEOUT("强制退出"), TIMEOUT("超时退出");
		private final String info;

		SessionOfflineType(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}
}
