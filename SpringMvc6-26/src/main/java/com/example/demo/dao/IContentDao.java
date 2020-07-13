package com.example.demo.dao;

import com.example.demo.domain.Content;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/30
 */
//@Repository
public interface IContentDao {
    boolean saveContent(String collectionName,Content content);

    List<Content> findAllContents(String collectionName);

    Content findByID(String collectionName,String id);

   // List<Content> findByTags(String collectionName,List<String> tags);
    List<Content> findByTag(String collectionName,String tag);
}
