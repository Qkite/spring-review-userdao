package com.likelion.dao_project.connectionmaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class LocalConnectionMaker implements ConnectionMaker{
    // 이제 사용X
    @Override
    public Connection getConnection() throws SQLException {

        Map<String, String> env = System.getenv();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/likelion-db", "root", "123456");

        return connection;
    }
}
