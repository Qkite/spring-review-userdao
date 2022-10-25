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
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void getAllTest() throws SQLException {
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        userDao.add(new User("1024", "2의 10제곱", "10241024"));

        List<User> usersInDB = userDao.getAll();

        // user들을 각각 불러올 때 새로운 인스턴스가 생성됨 --> 다르게 인식함
//        List<User> referenceList = new ArrayList<>();
//        referenceList.add(user1);
//        referenceList.add(user2);
//        referenceList.add(user3);
//        referenceList.add(new User("1024", "2의 10제곱", "10241024"));
        assertEquals(usersInDB.size(), 4);
    }


}