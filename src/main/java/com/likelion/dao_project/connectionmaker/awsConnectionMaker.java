package com.likelion.dao_project.connectionmaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class awsConnectionMaker implements ConnectionMaker{
    @Override
    public Connection getConnection() throws SQLException {

        Map<String, String> env = System.getenv();
        Connection connection = DriverManager.getConnection(env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
        

        return connection;
    }
}
