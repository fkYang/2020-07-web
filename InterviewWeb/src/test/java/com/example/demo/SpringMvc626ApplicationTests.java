package com.example.demo;

import com.example.demo.dao.ContentDaoIml;
import com.example.demo.domain.Content;
import com.example.demo.domain.ListContent;
import com.example.demo.domain.User;
import com.example.demo.service.ContentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InterviewWeb.class)
class SpringMvc626ApplicationTests {

    @Autowired
    MongoDatabaseFactory mongoDatabaseFactory;
    @Autowired
    MongoTemplate mongoTemplate ;
    @Autowired
    ContentDaoIml contentDao;

    @Autowired
    ContentService contentService;

    static String collectionName = "java";

    @Test
    void show(){
        System.out.println(mongoTemplate);
        System.out.println(mongoDatabaseFactory);

        MongoDatabaseFactory mongoDbFactory = mongoTemplate.getMongoDbFactory();
        //MongoTemplate mongo =MongoDBConnection.getTeplate();
        MongoTemplate mongo = mongoTemplate;
        Query query = new Query();
        List<User> users = mongo.find(query, User.class);
        for(User user:users){
            System.out.println(user.toString());
        }
        //MongoTemplate mongoTemplate = new M
    }
    @Test
    public void save(){
        Content content = new Content();
        content.setTitle("this is a test Demo2");
        content.setContent("简单的测试 插入的情况,列出多个");
        content.setUrl("no url");
        List<String> tags = new ArrayList<>();
        tags.add("demo");
        tags.add("java");
        content.setTags(tags);

        contentDao.saveContent(collectionName,content);

    }

    @Test
    void findByTags(){
        List<String> tags = new ArrayList<>();
        tags.add("JVM");
    //    tags.add("distributed-system");
        List<ListContent> listContents = contentService.findByTags(collectionName, tags);
        for(ListContent content:listContents)
            System.out.println(content.get_id() + "--" + content.getTitle());
    }

    @Test
    void findAll() {
        List<ListContent> allContent = contentService.findAllContent(collectionName);
        for(ListContent content:allContent)
            System.out.println(content.get_id());


        List<Content> contents = contentDao.findAllContents(collectionName);
        for(Content content:contents)
            System.out.println(content.get_id());
        contents = contentDao.findAllContents(collectionName);
        for(Content content:contents)
            System.out.println(content.get_id());
    }


}
