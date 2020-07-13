package com.example.demo.dao;


import com.example.demo.domain.Seen;
import com.example.demo.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/25
 */

//@Repository
public interface IUserDao {//extends MongoRepository<User, Long> {
    public User saveUser(User user);
    public List<User> findAll(User user);
    User findByName(String username);
    User updateUserSeen(User user);
    boolean updateUser(User user);
    //public User findUser(String loginName,String passWord);
}
