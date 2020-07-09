package com.example.demo.service;

import com.example.demo.dao.UserDaoIml;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/25
 */
@Service
public class UserService {


    @Autowired
    private UserDaoIml userDao;


    public boolean saveUser(User user){
        return  userDao.saveUser(user);
    }


    //    public User findUser(User user){
//        Query query = new Query();
//
//        Criteria criteria=Criteria.where("name").is(user.getName());//.and("password").is(user.getPassword());
//        query.addCriteria(criteria);
//
//        return  mongo.findOne(query,User.class);
//
//    }
    public List<User> findAll(User user){

        System.out.println("userDao:" + userDao);
        return  userDao.findAll(user);
    }

    public User findByUsername(String username){
        return userDao.findByName(username);
    }
}
