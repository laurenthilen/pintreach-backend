package com.laurenemick.pintreach.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig
{
    @Value("${local.run.db:H2}") // if local doesn't exist, default to H2
    private String dbValue;

    @Value("${spring.datasource.url:}")
    private String dbURL;

    String myUrlString;
    String myDriverClass;
    String myDBUser;
    String myDBPassword;

//    @Bean
//    public DataSource dataSource()
//    {
//        if (dbValue.equalsIgnoreCase("POSTGRESQL"))
//        {
//            // assumes heroku
//            HikariConfig config = new HikariConfig();
//            config.setDriverClassName("org.postgresql.Driver");
//            config.setJdbcUrl(dbURL);
//            return new HikariDataSource(config);
//        } else
//        {
//            // assumes h2
//            String myUrlString = "jdbc:h2:mem:testdb"; // how we're going to access db
//            String myDriverClass = "org.h2.Driver"; // which db we're going to use
//            String myDBUser = "sa";
//            String myDBPassword = "";
//
//            return DataSourceBuilder.create()
//                .username(myDBUser)
//                .password(myDBPassword)
//                .url(myUrlString)
//                .driverClassName(myDriverClass)
//                .build();
//        }
//    }
}