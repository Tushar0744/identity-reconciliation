//package com.identity_reconciliation.identity_reconciliation.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//  @Bean
//  @ConfigurationProperties(prefix = "spring.datasource")
//  public DataSource dataSource() {
//    return DataSourceBuilder.create().type(HikariDataSource.class).build();
//  }
//}