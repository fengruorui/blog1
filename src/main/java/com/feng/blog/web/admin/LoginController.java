package com.feng.blog.web.admin;

import com.feng.blog.po.User;
import com.feng.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by Feng on 2020/3/7.
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    @PostMapping("/login")
    public String login(String name, String password, HttpSession session , RedirectAttributes attributes){
        User user = userService.checkUser(name, password);
        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message", "用户名账户或密码错误");
            return "redirect:/admin";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
