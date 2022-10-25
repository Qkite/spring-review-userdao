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


    public void add(User user) {

        try {
            Connection c = connectionMaker.getConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {

        try {
            Connection c = connectionMaker.getConnection();


            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {

        try {
            Connection c = connectionMaker.getConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("delete from users");

            // Query문 실행
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCount(){

        try {
            Connection c = connectionMaker.getConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("select count(*) from users");

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int count  = rs.getInt(1);

            rs.close();
            pstmt.close();
            c.close();

            return count;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        LocalConnectionMaker localConnectionMaker = new LocalConnectionMaker();
        UserDao userDao = new UserDao(localConnectionMaker);
//        userDao.add();
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }
}
