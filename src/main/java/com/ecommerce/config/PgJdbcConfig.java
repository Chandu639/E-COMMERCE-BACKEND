package com.ecommerce.config;



	import javax.sql.DataSource;

	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.jdbc.core.JdbcTemplate;

	@Configuration
	public class PgJdbcConfig {

	    @Bean(name = "pgJdbcTemplate")
	    public JdbcTemplate pgJdbcTemplate(
	            @Qualifier("pgVectorDataSource")
	            DataSource dataSource) {

	        return new JdbcTemplate(dataSource);
	    }
	}


