package com.likelion.dao_project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStatement implements StatementStrategy{
    // 이제 사용X
    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("delete from users");
        return ps;
    }
}
