package com.likelion.dao_project.dao;

import com.likelion.dao_project.connectionmaker.ConnectionMaker;
import com.likelion.dao_project.connectionmaker.LocalConnectionMaker;
import com.likelion.dao_project.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {
    DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    UserDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void executeQueryDelete(String query){
        JdbcContext jdbcContext = new JdbcContext(dataSource);

        jdbcContext.jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query);
                return ps;
            }
        });

    }

    public void executeQueryAdd(User user, String query){
        JdbcContext jdbcContext = new JdbcContext(dataSource);

        jdbcContext.jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });

    }






    public void add(User user) throws SQLException {
        this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?,?,?);", user.getId(), user.getName(), user.getPassword());



        // executeQuery적용
        //        executeQueryAdd(user, "INSERT INTO users(id, name, password) VALUES(?,?,?);");

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

        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                return user;
            }
        };

        User result = this.jdbcTemplate.queryForObject("select * from users where id = ?", rowMapper, id);
        return result;

//        Connection c = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        User user = null;
//
//        try {
//            c = dataSource.getConnection();
//
//
//
//            pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
//            pstmt.setString(1, id);
//
//
//            rs = pstmt.executeQuery();
//            rs.next();
//            try {
//                user = new User(rs.getString("id"), rs.getString("name"),
//                        rs.getString("password"));
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//
//            if(rs!=null){
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if(pstmt!=null){
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if(c!=null){
//                try {
//                    c.close();
//                } catch (SQLException e) {
//
//                }
//            }
//
//        }
//        return user;
    }

    public void deleteAll() {

        this.jdbcTemplate.update("delete from users");

        // execute Query 활용
        //executeQueryDelete("delete from users");


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

        int count = this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);

        return count;
//        Connection c = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        int count;
//
//        try {
//            c = dataSource.getConnection();
//
//            // Query문 작성
//            pstmt = c.prepareStatement("select count(*) from users");
//
//            // Query문 실행
//            rs = pstmt.executeQuery();
//            rs.next();
//            count  = rs.getInt(1);
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if(rs!=null){
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if(pstmt!=null){
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if(c!=null){
//                try {
//                    c.close();
//                } catch (SQLException e) {
//
//                }
//            }
//
//        }
//        return count;
    }

    public List<User> getAll(){
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                return user;
            }
        };

        return this.jdbcTemplate.query("select * from users order by id", rowMapper);

    }


    public static void main(String[] args) throws SQLException {
        UserDaoFactory userDaoFactory = new UserDaoFactory();
        UserDao userDao = new UserDao(userDaoFactory.localdataSource());
//        userDao.add();
        User user = userDao.findById("6");
        System.out.println(user.getName());
    }
}
