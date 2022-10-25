package com.likelion.dao_project.dao;

import com.likelion.dao_project.connectionmaker.LocalConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;


@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao localConnection() throws SQLException {


        return new UserDao(localdataSource());
    }

    @Bean
    public DataSource localdataSource() throws SQLException {
        Map<String, String> env = System.getenv();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);

        dataSource.setUrl("jdbc:mysql://localhost:3306/likelion-db");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }


}
