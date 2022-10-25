package com.likelion.dao_project.connectionmaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class localConnectionMaker implements ConnectionMaker{
    @Override
    public Connection getConnection() throws SQLException {

        Map<String, String> env = System.getenv();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/likelion-db", "root", env.get("DB_PASSWORD"));

        return connection;
    }
}
