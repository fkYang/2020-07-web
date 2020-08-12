package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/26
 */
@Controller
@RequestMapping("/home")
public class HomeController {
   @GetMapping
    public String hellworld(@CookieValue("JSESSIONID")String cookie,
                            @RequestHeader("User-Agent")String header){
        System.out.println("cookie:"+cookie);
        System.out.println("header:"+header);
        System.out.println("helloworld");
        return "/home";
    }
}
