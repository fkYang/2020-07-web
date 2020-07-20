package com.example.demo.controller;

import com.example.demo.domain.ListContent;
import com.example.demo.domain.Seen;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

import com.example.demo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 *
 * @author yfk
 * @date 2020/6/26
 */
@Controller
@RequestMapping("/user")
//@SessionAttributes("user")
public class UserController {



    @Autowired
   private UserService userService;
  //  @Autowired
    @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;//用来实现用户加密的

    @GetMapping(value = "/login")
    public String getLogin(){
        return "/login";
    }

    @PostMapping(value = "/login")
    public  String login( User user , Model model, HttpServletRequest request){

        User user1 = userService.findByUsername(user.getUsername());
        if(user1 ==null || !bCryptPasswordEncoder.matches(user.getPassword(),user1.getPassword())){
            model.addAttribute("loginError", "登陆失败，用户不存在或者密码不正确");
       // etAttribute("errors.login", "登陆失败，用户不存在或者密码不正确");
            return "/login";
        }
        //      User user1 =(User) model.getAttribute("user");
   //     System.out.println("first:  "+user1);
        // public  String login(@Valid User user){

        HttpSession session = request.getSession(true);
        session.setAttribute("user", user1);



     //   System.out.println(user1.toString());
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String getRegesiter(){

        return "/register";
    }
    @PostMapping("/register")
    public String postRegister(User user,Model model){//,HttpServletRequest request ){
        //密码加密
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

//        System.out.println(request.toString());
//        Map<String, String[]> parameterMap = request.getParameterMap();
        boolean result = userService.saveUser(user);
        //判断是否存在用户
  //      System.out.println(user.toString());
        if(result){//sava成功，用户注册成功
            System.out.println(user.getUsername()+":"+user.toString());
            return "/login";
        }
        model.addAttribute("usernameError", "注册失败，用户名已被使用");
       return "/register";
    }


    @GetMapping("/self")
    public String self(HttpServletRequest request, @RequestParam Map<String, String> map ,Model model){

        User user = (User ) request.getSession().getAttribute("user");
        if(user == null )
            return "/login";
        List<Seen> seen = user.getSeen();
  //      List<String> classType = new ArrayList<>();
        model.addAttribute("seen", seen);
        return "/user";
    }
    @GetMapping("/selflist")
    public String selfList(HttpServletRequest request, @RequestParam Map<String, String> map ,Model model){

        String tag = map.get("collectionTag");
        model.addAttribute("collectionName",tag);
        User user = (User ) request.getSession().getAttribute("user");

        List<Seen> seen = user.getSeen();
        for(Seen seen1 : seen){
            if(seen1.getTag().equals(tag)){
                model.addAttribute("seenList", seen1.getContents());
                break;
            }
        }
        //      List<String> classType = new ArrayList<>();

        return "/userList";
    }
}
