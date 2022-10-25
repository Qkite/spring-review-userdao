package com.likelion.dao_project.dao;

import com.likelion.dao_project.connectionmaker.ConnectionMaker;
import com.likelion.dao_project.connectionmaker.LocalConnectionMaker;
import com.likelion.dao_project.domain.User;

import java.sql.*;

public class UserDao {
    ConnectionMaker connectionMaker;

    UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }


    public void jdbcContextWithStatementStrategy(StatementStrategy statementStrategy){
        Connection c = null;
        PreparedStatement pstmt = null;

        try {
            c = connectionMaker.getConnection();

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



    public void add(User user) throws SQLException {

        jdbcContextWithStatementStrategy(new AddStatement(user));

//        Connection c = null;
//        PreparedStatement pstmt = null;
//
//        if (user == null){
//            throw new RuntimeException();
//        }
//
//        try {
//            c = connectionMaker.getConnection();
//            AddStatement addStatement = new AddStatement(user);
//            pstmt = addStatement.makeStatement(c);
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if(pstmt!=null){
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if (c!=null){
//                try {
//                    c.close();
//                } catch (SQLException e) {
//
//                }
//            }
//
//        }
    }

    public User findById(String id) throws SQLException {
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            c = connectionMaker.getConnection();



            pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);


            rs = pstmt.executeQuery();
            rs.next();
            try {
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {

                }
            }
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
        return user;
    }

    public void deleteAll() {

        jdbcContextWithStatementStrategy(new DeleteStatement());
//        Connection c = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            c = connectionMaker.getConnection();
//
//            DeleteStatement deleteStatement = new DeleteStatement();
//
//            pstmt = deleteStatement.makeStatement(c);
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        finally {
//            if(pstmt!=null){
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//
//                }
//            }
//
//            if(c!=null){
//                try {
//                    c.close();
//                } catch (SQLException e) {
//
//                }
//            }
//
//        }
    }

    public int getCount(){
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count;

        try {
            c = connectionMaker.getConnection();

            // Query문 작성
            pstmt = c.prepareStatement("select count(*) from users");

            // Query문 실행
            rs = pstmt.executeQuery();
            rs.next();
            count  = rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {

                }
            }
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
        return count;
    }

    public static void main(String[] args) throws SQLException {
        LocalConnectionMaker localConnectionMaker = new LocalConnectionMaker();
        UserDao userDao = new UserDao(localConnectionMaker);
//        userDao.add();
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }
}
