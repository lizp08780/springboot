package com.lizp.springboot.config.dataSource;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
 * http://127.0.0.1:8010/druid/index.html
 * 
 * @Description: druid数据源状态监控.
 * @author 李智鹏
 * @date 2018年5月18日 下午12:39:50
 *
 */
@WebServlet(urlPatterns = "/druid/*", initParams = { @WebInitParam(name = "allow", value = "192.168.1.1,127.0.0.1"), // IP白名单(没有配置或者为空，则允许所有访问)
		@WebInitParam(name = "deny", value = "192.168.1.2"), // IP黑名单 (存在共同时，deny优先于allow)
		@WebInitParam(name = "loginUsername", value = "admin"), // 用户名
		@WebInitParam(name = "loginPassword", value = "123456"), // 密码
		@WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
})
public class DruidStatViewServlet extends StatViewServlet {

	private static final long serialVersionUID = 5933726772002531186L;

}
