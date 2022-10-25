package com.likelion.dao_project.dao;

import com.likelion.dao_project.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;

    User user1;
    User user2;
    User user3;
    @BeforeEach
    void setting(){

        userDao = context.getBean("localConnection", UserDao.class);

        userDao.deleteAll();
        user1 = new User("101", "수지", "10234");
        user2 = new User("102", "제니", "13516540");
        user3 = new User("103", "아이유", "16516");

    }

    @Test
    void addAndGet() throws SQLException {
        userDao.add(user1);
        userDao.add(user2);
        assertEquals(userDao.getCount(), 2);
        assertEquals(userDao.findById("101").getName(), user1.getName());


    }


}