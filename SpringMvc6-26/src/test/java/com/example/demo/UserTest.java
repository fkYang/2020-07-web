package com.example.demo;

import com.example.demo.dao.IUserDao;
import com.example.demo.dao.UserDaoIml;
import com.example.demo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/3
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringMvc626Application.class)
public class UserTest {
    @Autowired
    IUserDao userDao;

    @Test
    public void findAllUser(){
        List<User> users = userDao.findAll(new User());
        for(User user : users){
            System.out.println(user.toString());
        }
    }



}
