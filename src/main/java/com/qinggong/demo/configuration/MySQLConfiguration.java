package com.qinggong.demo.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class MySQLConfiguration {
	// 读取mysql数据源
	@Bean
	@Primary
	@Qualifier("masterDatasource")
	@ConfigurationProperties(prefix = "spring.data.mysql")
	public DataSource masterDatasource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "masterJdbcTemplate")
	protected JdbcTemplate getMasterJdbcTemplate(@Qualifier("masterDatasource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
}
