package com.example.demo.controller;

import com.example.demo.domain.Content;
import com.example.demo.service.ContentService;
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
import java.util.List;
import java.util.Map;

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
    int limitItem = 5;

    @GetMapping("/content")
    public String getDemo(@RequestParam Map<String, String> map, Model model){

        System.out.println("hello");
        System.out.println(map.toString());
        //初始化页数
        if (map.get("currentPage") == null|| map.get("currentPage").isEmpty()) {
            map.put("currentPage", "1");
        }
        String collectionName = map.get("studyType");
        model.addAttribute("collectionName", collectionName);
//        System.out.println(collectionName);
        List<Content> contents = contentService.findAllContent(collectionName);
     //   model.addAttribute("contents", contents);
     //   Object o =model.getAttribute("currentPage");
//        int i = Integer.valueOf(map.get("currentPage"));
 //       System.out.println("Content size:" + contents.size() + "--- :" + i);
        PageUtil<Content> pageInfo = new PageUtil<>(Integer.valueOf(map.get("currentPage")), limitItem, contents);
       // PageUtil<Content> pageInfo = new PageUtil<>(Integer.valueOf((Integer) model.getAttribute("currentPage")), 1, contents);
        model.addAttribute("contents", pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);

     //   System.out.println(pageInfo.getLimit()+"----limit" + "---------currentPages" + pageInfo.getPageNumber());
//        for(Content content : contents){
//            System.out.println(content.get_id() + "-- "+ content.getTitle());
//        }
   //     System.out.println("clike here");
        return "/content";
    }

    @ResponseBody
    @RequestMapping("/content/detail")
    public String getDetail(@Param("collectionName") String collectionName , @Param("id") String id , Model model){
   //    String collectionName = String.valueOf(model.getAttribute("studyType"));
       // String collectionName =collectionName;

        System.out.println(collectionName);
        String mdTest = contentService.findById(collectionName,id).getContent();
//        StringBuilder builder = new StringBuilder();
//        builder.append("<html>\n" + "<body>");
//        builder.append("<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">");
//        String text = mdTest;
//        builder.append(text);
//        builder.append("</pre></body>\n" + "</html>");
//        String html2 = builder.toString();
        //转化为md文档
        Document document = MarkDownUtils.PARSER.parse(mdTest);
        String html = MarkDownUtils.RENDERER.render(document);
        model.addAttribute("content",html);

        return html;
    }
}
