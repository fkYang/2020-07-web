package com.example.demo.dao;

import com.example.demo.domain.User;
import com.example.demo.utils.ExecutorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.*;


/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/25
 */

@Repository
public class UserDaoIml implements IUserDao {
  //  @Autowired
    MongoTemplate mongo;

    @Autowired
    MongoDatabaseFactory mongoDatabaseFactory;

    ExecutorService executorService = ExecutorUtils.getExecutorPool();

    @Override
    public  boolean saveUser(User user) {

        if(findByName(user.getUsername()) != null)
            return false;
        System.out.println("save user");

        //匿名内部类，创建 任务，提交至线程池实现
        Future<List<User>> submit = executorService.submit(new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                System.out.println("start  " + "--- mongo:" + mongo + " --" + Thread.currentThread().getName());
                mongo.save(user);
                return null;
            }
        });
         return  true;
    }

    @Override
    public List<User> findAll(User user) {

        //提交任务至线程池完成相应的链接
        Future<List<User>> submit = executorService.submit(new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                System.out.println("start  " + "--- mongo:" + mongo + " --" + Thread.currentThread().getName());
                Query query = new Query();
                List<User> users = mongo.find(query, User.class);
                return users;
            }
        });

        List<User> users = null;
        try {
            users = submit.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByName(String username) {
        Future<User> submit = executorService.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                Query query = new Query();
                query.addCriteria(Criteria.where("username").is(username));
                // Criteria.where("2").
                System.out.println("search--------------"+query.toString());
                User user = mongo.findOne(query, User.class);
                return user;
            }
        });

        User user = null;
        try {
            user = submit.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
