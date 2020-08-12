package com.example.demo.controller;

import com.example.demo.domain.Content;
import com.example.demo.domain.ListContent;
import com.example.demo.domain.User;
import com.example.demo.service.ContentService;
import com.example.demo.service.UserService;
import com.example.demo.utils.MarkDownUtils;
import com.example.demo.utils.PageUtil;
import com.example.demo.utils.RedisUtils;
import com.vladsch.flexmark.util.ast.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/30
 */
@Controller
@RequestMapping("/study")
public class ContentController {

    @Autowired
    ContentService contentService;

    @Autowired
    UserService userService;

    int limitItem = 5;

    @GetMapping("/content")//content列表
    public String getDemo(@RequestParam Map<String, String> map, Model model , HttpServletRequest request){
        System.out.println("content:"+map.toString());
        //初始化页数
        if (map.get("currentPage") == null|| map.get("currentPage").isEmpty()) {
            map.put("currentPage", "1");
        }
        String collectionName = map.get("collectionName");
        HttpSession session = request.getSession();
        session.setAttribute("collectionName", collectionName);
        model.addAttribute("collectionName", collectionName);
        List<ListContent> contents = contentService.findAllContent(collectionName);
        if(contents == null || contents.size() == 0){
            model.addAttribute("error","未找到相关信息:"+collectionName);
            return "/nameError";
        }

        PageUtil<ListContent> pageInfo = new PageUtil<>(Integer.valueOf(map.get("currentPage")), limitItem, contents);
       // PageUtil<Content> pageInfo = new PageUtil<>(Integer.valueOf((Integer) model.getAttribute("currentPage")), 1, contents);
        model.addAttribute("contents", pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);
        return "/content";
    }

    @ResponseBody
    @RequestMapping("/content/detail")//文档内容
    public String getDetail(@RequestParam Map<String, String> map  , Model model ,  HttpServletRequest request){
        //获取参数
        String collectionName  = map.get("collectionName");
        String id  = map.get("id");

      //  System.out.println(collectionName);
        Content content = contentService.findById(collectionName, id);
        if(content == null){
            model.addAttribute("error","未找到相关信息:"+id);
            return "/nameError";
        }
        String mdTest = content.getContent();

        //转化md文档
        Document document = MarkDownUtils.PARSER.parse(mdTest);
        String html = MarkDownUtils.RENDERER.render(document);
        model.addAttribute("content",html);

        User  user = (User) request.getSession().getAttribute("user");

        userService.addUserContent(ContentService.convert(content),collectionName,user);

        return html;
    }


    @RequestMapping("/content/name")//按照名字查找的结果集
    public String getNameList(@RequestParam Map<String, String> map  , Model model ,  HttpServletRequest request){
        //获取参数
        String collectionName  = map.get("collectionName");

        List<ListContent> contents;
        if(map.containsKey("keyWord")){
            String keyWord  = map.get("keyWord");
            contents = contentService.findByName(collectionName, keyWord);
            if(contents == null || contents.size() < 1){
                model.addAttribute("error","未找到相关信息");
                return "/nameError";
            }
        }else{
            return "/home";
        }

        model.addAttribute("contents", contents);
        model.addAttribute("collectionName", collectionName);
        return "/names";
    }


    @RequestMapping("/content/tags")//tag查找集合
    public String getTag(@RequestParam Map<String, String> map  , Model model,HttpServletRequest request){

        //初始化页数
        if (map.get("currentPage") == null|| map.get("currentPage").isEmpty()) {
            map.put("currentPage", "1");
        }
        String collectionName =(String) request.getSession().getAttribute("collectionName");
        //       System.out.println(collectionName);
        model.addAttribute("collectionName", collectionName);
//        System.out.println(collectionName);
        List<ListContent> contents = new ArrayList<>();
        List<String>  tags ;
        if(map.containsKey("tags")){
            String tag = map.get("tags");
            tags = Arrays.asList(tag.split(","));
        }else{
            tags = new ArrayList<>();
            String [] strings = {"JVM","distributed-system"};//------------------------------
            for(String s : strings){
                if(map.containsKey(s)){
                    tags.add(s);
                }
            }
        }
        List<ListContent> listContents = contentService.findByTags(collectionName, tags);
//null
        if(listContents == null || listContents.size() == 0){
            model.addAttribute("error","未找到相关信息:"+tags.toString());
            return "/nameError";
        }
        contents.addAll(listContents);

        PageUtil<ListContent> pageInfo = new PageUtil<>(Integer.valueOf(map.get("currentPage")), limitItem, contents);
        // PageUtil<Content> pageInfo = new PageUtil<>(Integer.valueOf((Integer) model.getAttribute("currentPage")), 1, contents);
        model.addAttribute("contents", pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", tags);
        return "/tags";

    }
}