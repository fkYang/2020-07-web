package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/6
 */
@Repository
public class StoreDB {
    @Autowired
    MongoTemplate mongo;

    public  void storeContent(String collectionName,Content content){
        Content content1 = mongo.save(content,collectionName);
     //   System.out.println(content1.get_id());

    }
    public  void storeContents(String collectionName,List<Content> contents){

        for(Content content:contents)
            mongo.save(content,collectionName);
    }

    public List<Content> findByTags(String collectionName,List<String> tags){
        Query query = new Query();
        query.addCriteria(Criteria.where("tags").in(tags));
        List<Content> contents = null;
        try {
            contents = mongo.find(query, Content.class,collectionName);
        }catch (Exception e){
           // System.out.println(e.printStackTrace(););
            e.printStackTrace();
            System.out.println("null p");
        }
        return contents;
    }


    public void storeJava(Content content) {
        storeContent("java",content);
    }
}
