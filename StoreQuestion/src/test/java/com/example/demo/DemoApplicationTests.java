package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.*;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
class DemoApplicationTests {

    @Autowired
    MongoTemplate mongo;
    @Autowired
    StoreDB storeDB;
    @Test
    void contextLoads() {

        List<String> tags = new LinkedList<>();
        tags.add("jvm");
        Query query = new Query();
     //   query.addCriteria(new Criteria().where("tags").in(tags));
        List<Content> contents = mongo.find(query, Content.class, "java");
        for(Content content:contents)
            System.out.println(content.get_id());
    }

    @Test
    void storeDBJava() throws Exception {
        String encoding = "UTF-8";
        String filePath = "/Users/haowentao/Documents/GitHub/interview-c-cpp/test";
        File fileRoot = new File(filePath);
        File[] tempList = fileRoot.listFiles();
        Map<String,String> map = new HashMap<>();
        for (File file : tempList) {
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            //title:
            String s = bufferedReader.readLine();
            System.out.println(s + "---");

            String[] split = s.split("=");
            map.put(split[0],split[1]);
            //url:
            s = bufferedReader.readLine();
            split = s.split("=");
            map.put(split[0],split[1]);
            //tages
            s = bufferedReader.readLine();
            split = s.split("=");
            map.put(split[0],split[1]);
            //content
            // bufferedReader.read
            StringBuilder builder = new StringBuilder();
            while (bufferedReader.ready()){
                builder.append(bufferedReader.readLine() + "\n");
            }
          //  map.put("content", builder.toString());
            Content content = new Content();
            content.setTitle(map.get("title"));
            content.setUrl(map.get("url"));
            content.setTags(Arrays.asList( map.get("tags").split(",")));
            content.setContent(builder.toString());
            System.out.println(content.toString());
            storeDB.storeJava(content);

        }
    }


    @Test
    void saveContetn(){
        Content content = new Content();
        String title = "tite";
        String url = "url";
        List<String> tags = new LinkedList<>();
        tags.add("a");
        tags.add("b");

        content.setTags(tags);
        content.setTitle(title);
        content.setUrl(url);
        mongo.save(content);
    }
    @Test
    void findByTag(){
        List<String> tags = new LinkedList<>();
        tags.add("a");
  //      tags.add("b");
   //     tags.add("c");
  //      String string = "a";
        List<Content> contents = storeDB.findByTags("java", tags);
        if(contents == null)
            return;
        for(Content content : contents){
            System.out.println("----"+content.toString());
        }
    }



}
