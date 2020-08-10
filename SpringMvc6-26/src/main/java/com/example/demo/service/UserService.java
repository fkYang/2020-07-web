package com.example.demo.service;

import com.example.demo.controller.UserController;
import com.example.demo.dao.IUserDao;
import com.example.demo.dao.UserDaoIml;
import com.example.demo.domain.ListContent;
import com.example.demo.domain.Seen;
import com.example.demo.domain.User;
import com.example.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
    private IUserDao userDao;
    @Autowired
    private RedisUtils redisUtils;


    static String cacheName = "users";
    public boolean saveUser(User user){
        if(userDao.saveUser(user) != null )
            return  true;
        return false;
 //       return  userDao.saveUser(user);
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
        List<User> users = userDao.findAll(user);
        for(User user1:users){
            redisUtils.hmSet(cacheName, user1.getUsername(), user);
            redisUtils.setUserExpireTime(cacheName);
        }
        return  users;
    }

    public User findByUsername(String username){
        User user = (User)redisUtils.hmGet(cacheName, username);
        //查询缓存
        if(user== null ){
            //查询数据库
            user = userDao.findByName(username);
            //设置缓存
            if(user != null){
                redisUtils.hmSet(cacheName, username, user);
                redisUtils.setUserExpireTime(cacheName);
            }
        }
        return user;
    }


    public void addUserContent(ListContent content,String collectionName,User user){
        if(user == null )
            return;
        List<Seen> seen = user.getSeen();
        boolean find = false;
        if(seen == null){
            seen = new ArrayList<>();
        }
        for( Seen temp : seen){
            if(temp.getTag().equals(collectionName)){
                temp.getContents().add(content);
                find = true;
                break;
            }
        }
        if(!find){


            user.setSeen(seen);

            Seen temp = new Seen();
            temp.setTag(collectionName);
            temp.setContents(new HashSet<>());
            temp.getContents().add(content);

            seen.add(temp);
        }

        userDao.updateUserSeen(user);
        redisUtils.hmSet(cacheName, user.getUsername(), user);

    }
}
