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
        List<Content> contents = null;
        //search DB
        Future<List<Content>> future = executorService.submit(new Callable<List<Content>>() {
            @Override
            public List<Content> call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                Query query = new Query();
                List<Content> inContents = mongo.find(query, Content.class,collectionName);
                return inContents;
            }
        });
        //mongo = MongoDBConnection.getTeplate();
        try {
            contents = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }

    @Override
    public Content findByID(String collectionName,String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        List<Content> contents = find(collectionName, query);
        if(contents != null && contents.size()>0)
            return contents.get(0);
        return null;
    }

    public List<Content> find(String collectionName,Query query){
        //查询数据库
        Future<List<Content>> submit = null;
        submit = executorService.submit(new Callable<List<Content>>() {
            @Override
            public List<Content> call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
//                Query query = new Query();
//                query.addCriteria(Criteria.where("_id").is(id));
                List<Content> contents = mongo.find(query, Content.class, collectionName);
                return contents;
            }
        });
        List<Content> contents = null;
        try {
            contents = submit.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;
    }


    @Override
    public List<Content> findByName(String collectionName, String keyWord) {
        Query query = Query.query(Criteria.where("title").regex(keyWord));
        List<Content> contents = find(collectionName, query);
        return contents;
    }

    @Override
    public List<Content> findByTag(String collectionName,String tag) {
        List<Content> contents = null;
        Future<List<Content>> future = executorService.submit(new Callable<List<Content>>() {
            @Override
            public List<Content> call() throws Exception {
                mongo = new MongoTemplate(mongoDatabaseFactory);
                Query query = new Query();
                query.addCriteria(Criteria.where("tags").in(tag));
                List<Content> contents = mongo.find(query, Content.class,collectionName);
                return contents;
            }
        });
        //mongo = MongoDBConnection.getTeplate();
        try {
            contents = future.get();
 //           cache.put(collectionName, contents);//加入缓存
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contents;

    }

}
