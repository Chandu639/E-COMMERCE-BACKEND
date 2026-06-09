package com.ecommerce.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
	public class MySqlConfig {

	    @Primary
	    @Bean
	    public DataSource mysqlDataSource() {

	        return DataSourceBuilder.create()
	                .driverClassName("com.mysql.cj.jdbc.Driver")
	                .url("jdbc:mysql://localhost:3306/ecommercedb")
	                .username("root")
	                .password("root")
	                .build();
	    }
	}

