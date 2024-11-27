package com.alibabacloud.mse.demo.c;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@Component
public class DataSourceConfiguration {

    @Bean
    @Primary
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.alibabacloud.mse.demo.c.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        config.setUsername("root");
        config.setPassword("root");
        config.setMaximumPoolSize(500);
        config.setMinimumIdle(10);
        return new HikariDataSource(config);
    }
}
