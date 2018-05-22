package com.lizp.springboot.shiro;

public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = -478034808343470680L;
	private String captcha;
	private boolean thirdParty;

	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String captcha,
			boolean thirdParty) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.thirdParty = thirdParty;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isThirdParty() {
		return thirdParty;
	}
}
