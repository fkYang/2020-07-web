package com.example.demo;

import com.example.demo.dao.IUserDao;
import com.example.demo.dao.UserDaoIml;
import com.example.demo.domain.Seen;
import com.example.demo.domain.User;
import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
    @Autowired
    MongoTemplate mongo;

//    @Test
//    public void findAllUser(){
//        List<User> users = userDao.findAll(new User());
//        for(User user : users){
//            System.out.println(user.toString());
//        }
//    }
//
//
//    @Test
//    public void update(){
//        User user = new User();
//        user.setPassword("123");
//        user.setUsername("123");
//       user =  userDao.saveUser(user);
//
//       List<Seen> seenList = new ArrayList<>();
//        Seen seen = new Seen();
//        seen.setTag("insert");
//            List<String> strings = new LinkedList<>();
//            strings.add("insert1");
//            strings.add("insert2");
//    //    seen.setContents(strings);
//        seenList.add(seen);
//        user.setSeen(seenList);
//
//
//        Query query = Query.query(Criteria.where("_id").is(user.get_id()));
//        Update update = new Update();
//        //    update.unset("seen");
//        update.set("seen",user.getSeen());
//        //  update.addToSet("seen",user.getSeen());
//        //   update.set("seen.$.contents", seen.getContents());
//        UpdateResult users = mongo.updateFirst(query, update, "users");
//        System.out.println(users+"---------------------------");
//     //   userDao.updateUser(user);
//     //   userDao.updateUserSeen(seen);
//    }


}
