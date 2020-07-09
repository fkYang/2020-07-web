package com.example.demo.dao;


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
    public boolean saveUser(User user);
    public List<User> findAll(User user);
    User findByName(String username);
    //public User findUser(String loginName,String passWord);
}
