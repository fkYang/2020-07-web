package com.example.demo;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/6
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
public class MongoStoreMain {

    @Autowired
   StoreDB storeDB;

    @Test
   public void storeDBJavas() throws Exception {
        String encoding = "UTF-8";

        String filePath = "/Users/haowentao/xiaoxueqi/need-to-write-Questions";
        String collectionName = "question";
        List<Content> contents = getContents(filePath,encoding);
        storeDB.storeContents(collectionName,contents);
       // javaStore.storeJavas(contents);
    }


    public List<Content> getContents(String filePath ,String encoding) throws Exception{
        List<Content> contents = new LinkedList<>();

        File fileRoot = new File(filePath);
        File[] tempList = fileRoot.listFiles();
        Map<String,String> map = new HashMap<>();
        for (File file : tempList) {
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);

            String s ;
            String[] split;
            //title:
            s = bufferedReader.readLine();
            split = s.split("=");
            if(split.length==1)
                continue;
            map.put(split[0],split[1]);//title
            //urlstart

            //s = bufferedReader.readLine();
            //split = s.split("=");
            //map.put(split[0],split[1]);
            //:urlEnd


            //tages
            s = bufferedReader.readLine();
            split = s.split("=");
            map.put(split[0],split[1]);
            //content
            // bufferedReader.read
            StringBuilder builder = new StringBuilder();
            while (bufferedReader.ready()){
                builder.append(bufferedReader.readLine() + "\r\n");
            }
            //  map.put("content", builder.toString());
            Content content = new Content();
            content.setTitle(map.get("title"));

            //content.setUrl(map.get("url"));

            content.setTags(Arrays.asList( map.get("tags").split(",")));
            content.setContent(builder.toString());
            System.out.println(content.toString());
            contents.add(content);

        }
        return  contents;
    }

}
