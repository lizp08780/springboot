package com.lizp.springboot.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;

@Controller
public class IndexController {

	@Autowired
	private DataSource dataSource;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model) {
		DruidDataSource a = (DruidDataSource) dataSource;
		System.err.println(a.getUrl());
		System.err.println(a.getUsername());
		System.err.println(a.getPassword());
		System.err.println(a.getDbType());
		System.err.println(a.getInitialSize());
		System.err.println(a.getMaxActive());
		System.err.println(a.getMinIdle());
		System.err.println(a.getMaxWait());
		System.err.println(a.isPoolPreparedStatements());
		System.err.println(a.getMaxPoolPreparedStatementPerConnectionSize());
		System.err.println(a.getValidationQuery());
		System.err.println(a.getValidationQueryTimeout());
		System.err.println(a.isTestOnBorrow());
		System.err.println(a.isTestOnReturn());
		System.err.println(a.isTestWhileIdle());
		System.err.println(a.isKeepAlive());

		System.err.println(a.getQueryTimeout());
		System.err.println(a.getTimeBetweenConnectErrorMillis());
		System.err.println(a.getTimeBetweenEvictionRunsMillis());
		System.err.println(a.getTimeBetweenLogStatsMillis());
		System.err.println(a.getMinEvictableIdleTimeMillis());
		System.err.println(a.getNumTestsPerEvictionRun());
		System.err.println(a.getFilterClassNames());
		model.addAttribute("name", "Dear");
		String sql = "update t set name = 'x' where id < 100 limit 10";
		String result = SQLUtils.format(sql, JdbcConstants.MYSQL);
		System.out.println(result); // 缺省大写格式
		return "index";
	}

	@RequestMapping(value = "/index2", method = RequestMethod.GET)
	public String index2(Model model) {
		model.addAttribute("name", "Dear");
		return "index2";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/error/404", method = RequestMethod.GET)
	public String error404() {
		return "errors/404";
	}

	@RequestMapping(value = "/error/500", method = RequestMethod.GET)
	public String error500() {
		return "errors/500";
	}

}
