package com.ecommerce.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PgVectorConfig {

    @Bean(name = "pgVectorDataSource")
    public DataSource pgVectorDataSource() {

        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5433/ecommerce_ai")
                .username("postgres")
                .password("postgres")
                .build();
    }
}

 

