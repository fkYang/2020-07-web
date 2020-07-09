package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/3
 */
@Repository
public class MongoDBConnection {

        @Autowired
     static MongoDatabaseFactory mongoDatabaseFactory;

    private static  MongoTemplate mongoTemplate;



     static public MongoTemplate getTeplate(){
        System.out.println(mongoTemplate);
        //System.out.println(mongoTemplate);
        MongoTemplate mon = new MongoTemplate(mongoTemplate.getMongoDbFactory());
        //MongoTemplate mon = new MongoTemplate(mongoDatabaseFactory);
       return  mon;
    }

}
