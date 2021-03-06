package com.lizp.springboot.controller.system;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.lizp.springboot.util.Message;

@Controller
@RequestMapping("/captcha")
public class CaptchaController {
	private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);
	@Resource(name = "captchaProducer")
	private Producer captchaProducer;
	@Resource(name = "captchaProducerMath")
	private Producer captchaProducerMath;

	/**
	 * 验证码生成
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/captchaImage")
	public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream out = null;
		try {
			HttpSession session = request.getSession();
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");

			String type = request.getParameter("type");
			String capStr = null;
			String code = null;
			BufferedImage bi = null;
			if ("math".equals(type)) {
				String capText = captchaProducerMath.createText();
				capStr = capText.substring(0, capText.lastIndexOf("@"));
				code = capText.substring(capText.lastIndexOf("@") + 1);
				bi = captchaProducerMath.createImage(capStr);
			} else if ("char".equals(type)) {
				capStr = code = captchaProducer.createText();
				bi = captchaProducer.createImage(capStr);
			}
			session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);// 把验证码存入到shiro管理的session中
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch (Exception e) {
			logger.error("验证码生成", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("验证码生成", e);
			}
		}
		return null;
	}

	/**
	 * 验证码验证
	 * 
	 * @param kaptchaCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/captchaVerify")
	@ResponseBody
	public Message captchaVerify(String kaptchaCode, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			Object obj = session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
			String code = String.valueOf(obj != null ? obj : "");
			if (StringUtils.isNotBlank(kaptchaCode) && kaptchaCode.equalsIgnoreCase(code)) {
				return Message.success("验证码正确");
			}
		} catch (Exception e) {
			logger.error("验证码验证", e);
			return Message.error("验证码验证错误：" + e.getMessage());
		}
		return Message.error("验证码错误");
	}
}
