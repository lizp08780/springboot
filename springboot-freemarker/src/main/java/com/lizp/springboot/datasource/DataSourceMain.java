package com.lizp.springboot.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "com.lizp.springboot.mapper.main", sqlSessionTemplateRef = "mainSqlSessionTemplate")
public class DataSourceMain {
	@Bean(name = "mainDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.main")
	// @Primary
	public DataSource testDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "mainSqlSessionFactory")
	// @Primary
	public SqlSessionFactory testSqlSessionFactory(@Qualifier("mainDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/main/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "mainTransactionManager")
	// @Primary
	public DataSourceTransactionManager testTransactionManager(@Qualifier("mainDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "mainSqlSessionTemplate")
	// @Primary
	public SqlSessionTemplate testSqlSessionTemplate(
			@Qualifier("mainSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
