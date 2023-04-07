package com.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.entity.User;
import com.blog.service.UserService;
import com.blog.util.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * 后台登录控制器
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * 跳转登录
     * @return
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    /**
     * 登录校验
     * @param username
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        User user=userService.getOne(queryWrapper.eq("username",username)
                .eq("password",password));
        if (user!=null){
            session.setAttribute("user",user);
            BaseContext.setCurrentId(user.getId());
            return "admin/index";
        }
        else{
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * 注销登录
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public  String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
