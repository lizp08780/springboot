package com.lizp.springboot.configuration;

import java.nio.charset.Charset;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @Description: RestTemplate客户端连接池配置
 * @author 李智鹏
 * @date 2018年6月5日 下午3:34:10
 *
 */
@Configuration
public class RestClientConfig {
	private static Logger logger = LoggerFactory.getLogger(RestClientConfig.class);

	@Bean
	public RestTemplate restTemplate() {
		logger.info("================");
		RestTemplate restTemplate = new RestTemplate();
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setReadTimeout(5000);
		requestFactory.setConnectTimeout(5000);
		restTemplate.setRequestFactory(requestFactory);
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

		// 更改StringHttpMessageConverter默认编码
		Iterator<HttpMessageConverter<?>> iterator = restTemplate.getMessageConverters().iterator();
		while (iterator.hasNext()) {
			final HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof StringHttpMessageConverter) {
				iterator.remove();
			}
		}
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

}
