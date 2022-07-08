package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
//@RequestMapping("/app")
public class LoginController {

    Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/app-secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("/app-error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }


//    @RequestMapping("/userinfo")
//    private StringBuffer getUsernamePasswordLoginInfo(Principal user)
//    {
//        StringBuffer usernameInfo = new StringBuffer();
//
//        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
//        if(token.isAuthenticated()){
//            User u = (User) token.getPrincipal();
//            usernameInfo.append("Welcome, " + u.getUsername());
//        }
//        else{
//            usernameInfo.append("NA");
//        }
//        return usernameInfo;
//    }
}
