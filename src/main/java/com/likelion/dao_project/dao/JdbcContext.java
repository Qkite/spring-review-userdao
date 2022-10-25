package com.likelion.dao_project.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {

    DataSource dataSource;

    public JdbcContext(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy){
        Connection c = null;
        PreparedStatement pstmt = null;

        try {
            c = dataSource.getConnection();

            pstmt = statementStrategy.makeStatement(c);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {

                }
            }

            if(c!=null){
                try {
                    c.close();
                } catch (SQLException e) {

                }
            }

        }

    }

}
