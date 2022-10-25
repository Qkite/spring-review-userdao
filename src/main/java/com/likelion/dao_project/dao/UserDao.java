package com.likelion.dao_project.dao;

import com.likelion.dao_project.connectionmaker.ConnectionMaker;
import com.likelion.dao_project.connectionmaker.LocalConnectionMaker;
import com.likelion.dao_project.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {
    ConnectionMaker connectionMaker;

    UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker =connectionMaker;
    }


    public void add(User user) throws SQLException {
        Connection c = null;
        PreparedStatement pstmt = null;

        if (user == null){
            throw new RuntimeException();
        }

        try {
            c = connectionMaker.getConnection();

            // Query문 작성
            pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            pstmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(pstmt!=null){
                try {
                    pstmt.close();
                } catch (SQLException e) {

                }
            }
            if (c!=null){
                try {
                    c.close();
                } catch (SQLException e) {

                }
            }

        }
    }

    public User findById(String id) throws SQLException {
        Connection c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            c = connectionMaker.getConnection();


            // Query문 작성
            pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
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
        Connection c = null;
        PreparedStatement pstmt = null;

        try {
             c = connectionMaker.getConnection();

            // Query문 작성
            pstmt = c.prepareStatement("delete from users");

            // Query문 실행
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
