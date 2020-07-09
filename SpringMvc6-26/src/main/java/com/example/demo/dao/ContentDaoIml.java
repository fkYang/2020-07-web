package com.example.demo.dao;

import com.example.demo.domain.Content;
import com.example.demo.utils.ExecutorUtils;
import com.example.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.*;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/30
 */
@Repository
public class ContentDaoIml implements  IContentDao{
    //cache,基于redis数据库做缓存
    @Autowired
    RedisUtils redisUtils;
    /**
     * 缓存约束：
     *      findall：缓存，name，hashset
     *      collectioname id value
     *      findById ，hashset， name-set集合，id--neirong
     *
     */
    ExecutorService executorService = ExecutorUtils.getExecutorPool();

 //   static int maxLength = 100;


    @Autowired  //获取创建数据库连接的工厂
    MongoDatabaseFactory mongoDatabaseFactory;

  //  @Autowired
    MongoTemplate mongo;

    @Override
    public boolean saveContent(String collectionName,Content content) {
        //提交事务，进行查询，限制数据库的连接
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                MongoTemplate mongo = new MongoTemplate(mongoDatabaseFactory);
                mongo.save(content,collectionName);
            }
        });
        return true;
    }

    @Override//查询所有
    public List<Content> findAllContents(String collectionName) {
        //cache search
        redisUtils.exists(collectionName);
        if(redisUtils.exists(collectionName)){
           // List objects =
            List<Content> list =  redisUtils.hmGetAll(collectionName,new Content());
            System.out.println("from cache all");
            return list;
        }
        //search DB
        Future<List<Content>> future = executorService.submit(new Callable<List<Content>>() {
            @Override
            public List<Content> call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                Query query = new Query();
                List<Content> contents = mongo.find(query, Content.class,collectionName);
                return contents;
            }
        });
        //mongo = MongoDBConnection.getTeplate();
        List<Content> contents = null;
        try {
            contents = future.get();
            //缓存更新
            for(Content content:contents){
                redisUtils.hmSet(collectionName, content.get_id(), content);
            }
            redisUtils.setContetnExpireTime(collectionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }
    @Override
    public Content findByID(String collectionName,String id) {


        if(redisUtils.exists(collectionName)){
            Content content = (Content)redisUtils.hmGet(collectionName, id);
            System.out.println("from cache content----------");
            if(content!= null)
                return content;
        }
//查询数据库，应该不需要了
        Future<Content> submit = null;
        submit = executorService.submit(new Callable<Content>() {
            @Override
            public Content call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                Query query = new Query();
                query.addCriteria(Criteria.where("_id").is(id));
              //  System.out.println("search--------------"+query.toString());
                Content content = mongo.findOne(query, Content.class,collectionName);
                return content;
            }
        });
        Content content = null;
        try {
            content = submit.get();
            redisUtils.hmSet(collectionName, content.get_id(), content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @Override
    public List<Content> findByTags(String collectionName,List<String> tags) {
        Future<List<Content>> future = executorService.submit(new Callable<List<Content>>() {
            @Override
            public List<Content> call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                Query query = new Query();
                query.addCriteria(Criteria.where("tags").in(tags));
                List<Content> contents = mongo.find(query, Content.class,collectionName);
                return contents;
            }
        });
        //mongo = MongoDBConnection.getTeplate();
        List<Content> contents = null;
        try {
            contents = future.get();
 //           cache.put(collectionName, contents);//加入缓存
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;

    }
}
