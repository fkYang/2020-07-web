package com.example.demo.controller;

import com.example.demo.domain.Question;
import com.example.demo.service.QuestionService;
import com.example.demo.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/7/17
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    int limitItem= 1;
    @Autowired
    QuestionService questionService;

    @GetMapping
    public  String questionGet(@RequestParam Map<String, String> map, Model model , HttpServletRequest request){
        System.out.println("content:"+map.toString());
        //初始化页数
        if (map.get("currentPage") == null|| map.get("currentPage").isEmpty()) {
            map.put("currentPage", "1");
        }

        List<Question> questions = questionService.getAll();

        PageUtil<Question> pageInfo = new PageUtil<>(Integer.valueOf(map.get("currentPage")), limitItem, questions);
        // PageUtil<Content> pageInfo = new PageUtil<>(Integer.valueOf((Integer) model.getAttribute("currentPage")), 1, contents);
        model.addAttribute("questions", pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);

        return "/question";
    }
}
