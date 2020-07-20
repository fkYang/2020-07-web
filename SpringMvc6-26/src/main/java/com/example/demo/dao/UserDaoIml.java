package com.example.demo.dao;

import com.example.demo.domain.ListContent;
import com.example.demo.domain.Seen;
import com.example.demo.domain.User;
import com.example.demo.utils.ExecutorUtils;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    static   String collectionName = "users";
  //  @Autowired
    MongoTemplate mongo;

    @Autowired
    MongoDatabaseFactory mongoDatabaseFactory;

    ExecutorService executorService = ExecutorUtils.getExecutorPool();

    @Override
    public  User saveUser(User user) {

        User user1 = findByName(user.getUsername());
        if(user1 != null)
            return null;
        System.out.println("save user");

        //匿名内部类，创建 任务，提交至线程池实现
        Future<User> submit = executorService.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                System.out.println("start  " + "--- mongo:" + mongo + " --" + Thread.currentThread().getName());
                User save = mongo.save(user, collectionName);
                return save;
            }
        });
        user1 = null;
        try {
            user1 = submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  user1;
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


//    public User updateUserSeen(Seen seen) {
//        Future<User> submit = executorService.submit(new Callable<User>() {
//            @Override
//            public User call() throws Exception {
//                mongo = new MongoTemplate(mongoDatabaseFactory);
//                Query query = Query.query(Criteria.where("seen.tag").is(seen.getTag()));
//
//
//                Update update = new Update();
//                update.unset("seen.$");
//                update.addToSet("seen",seen);
//             //   update.set("seen.$.contents", seen.getContents());
//                mongo.updateFirst(query, update, "users");
////                // Criteria.where("2").
////                System.out.println("search--------------"+query.toString());
////                User user = mongo.findOne(query, User.class);
//                return null;
//            }
//        });
//        return null;
//    }


    @Override
    public User updateUserSeen(User user ) {
        Future<User> submit = executorService.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                Query query = Query.query(Criteria.where("_id").is(user.get_id()));
                Update update = new Update();
            //    update.unset("seen");
                update.set("seen",user.getSeen());
              //  update.addToSet("seen",user.getSeen());
                //   update.set("seen.$.contents", seen.getContents());
                UpdateResult users = mongo.updateFirst(query, update, "users");
                System.out.println(users+"---------------------------");
//                // Criteria.where("2").
//                System.out.println("search--------------"+query.toString());
//                User user = mongo.findOne(query, User.class);
                return null;
            }
        });
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

}
