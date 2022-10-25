package com.likelion.dao_project.dao;

import com.likelion.dao_project.connectionmaker.LocalConnectionMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;


@Configuration
public class UserDaoFactory {

    @Bean
    public UserDao localConnection() throws SQLException {

        LocalConnectionMaker localConnectionMaker = new LocalConnectionMaker();

        return new UserDao(localConnectionMaker);
    }


}
