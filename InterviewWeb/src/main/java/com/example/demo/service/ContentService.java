package com.example.demo.service;


import com.example.demo.dao.IContentDao;
import com.example.demo.domain.Content;
import com.example.demo.domain.ListContent;
import com.example.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Autowired
    RedisUtils redisUtils;
    public void saveContent(String collectionName,Content content){
        contentDao.saveContent(collectionName,content);
    }
    private List<Content> findAllRealContent(String collectionName){
        List<Content> contents = null;
        if(redisUtils.exists(collectionName)){ //cache search
            contents =  redisUtils.hmGetAll(collectionName,new Content());
            //  System.out.println("from cache all");
        }else{//数据库查询
            contents = contentDao.findAllContents(collectionName);
            //缓存更新  hmset   id    content
            for(Content content:contents){
                redisUtils.hmSet(collectionName, content.get_id(), content);
            }
        }
        return contents;
    }

    public List<ListContent> findAllContent(String collectionName){

        List<Content> contents = findAllRealContent(collectionName);
        List<ListContent> listContents = convertList(contents);
        //设置缓存时间
        redisUtils.setContetnExpireTime(collectionName);
        return listContents;
    }
    public Content findById(String collectionName,String id){
        Content content = null;
        if(redisUtils.exists(collectionName)){
            content = (Content)redisUtils.hmGet(collectionName, id);
            System.out.println("from cache content----------");
            if(content!= null)
                return content;
        }else{
            content = contentDao.findByID(collectionName, id);
            if(content == null )
                return null;
            redisUtils.hmSet(collectionName, content.get_id(), content);
        }
        return content;
    }

    public List<ListContent> findByTags(String collectionName,List<String> tags){
        List<ListContent> listContents =new ArrayList<>();
        for( String tag:tags){
            String name = collectionName + ":" + tag;
            List<ListContent> listContentstemp = null;
            //查询缓存
            if(redisUtils.exists(name)){
                listContentstemp = new ArrayList<>();
                Set<Object> objects = redisUtils.setMembers(name);
                for(Object o:objects){
                    listContentstemp.add((ListContent)o);
                }
            }
            else{//查询数据库，获取对应tag的数据
                List<Content> contents = contentDao.findByTag(collectionName, tag);
                listContentstemp = convertList(contents);
                if( listContentstemp == null)
                    return null;
                //更新缓存，tag缓存
                for(ListContent listContent:listContentstemp){
                    redisUtils.add(name, listContent);
                }

            }
            //加入结果集合

            listContents.addAll(listContentstemp);
            //设置缓存时间
            redisUtils.setContetnExpireTime(name);
        }
        return listContents;
    }
    public List<ListContent> findByName(String collectionName,String keyWord){

        List<Content> contents = contentDao.findByName(collectionName, keyWord);
        List<ListContent> listContents = convertList(contents);
        return listContents;
    }

    static public ListContent convert(Content content){
        if(content == null)
            return null;
        ListContent listContent = new ListContent();
        listContent.set_id(content.get_id());
        listContent.setTags(content.getTags());
        listContent.setTitle(content.getTitle());
        listContent.setUrl(content.getUrl());
        return  listContent;
    }
    static private List<ListContent> convertList(List<Content> contents){
        if(contents == null)
            return null;

        List<ListContent> listContent = new ArrayList<>();
        for( Content content:contents){
            listContent.add(convert(content));
        }
        return  listContent;
    }


}
