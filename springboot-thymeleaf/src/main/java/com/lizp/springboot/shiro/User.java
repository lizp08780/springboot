package com.lizp.springboot.shiro;

public class User {
	private String username;
	private String password;
	/**
	 * 16进制，如果没有salt返回空或null
	 */
	private String salt;
	private boolean locked;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return "User{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", salt='" + salt + '\''
				+ ", locked=" + locked + '}';
	}
}
