package com.example.demo.service;

import com.example.demo.dao.ContentDaoIml;
import com.example.demo.dao.IContentDao;
import com.example.demo.domain.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/30
 */
@Service
public class ContentService {
    @Autowired
    IContentDao contentDao;
    public void saveContent(String collectionName,Content content){
        contentDao.saveContent(collectionName,content);
    }

    public List<Content> findAllContent(String collectionName){
        return contentDao.findAllContents(collectionName);
    }

    public Content findById(String collectionName,String id){
        return contentDao.findByID(collectionName,id);
    }

    public List<Content> findByTags(String collectionName,List<String> tags){
        return contentDao.findByTags(collectionName, tags);
    }
}
