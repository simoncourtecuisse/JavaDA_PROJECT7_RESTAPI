package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;

@Controller
//@RequestMapping("/app")
public class LoginController {

    Logger LOGGER = LogManager.getLogger(LoginController.class);

    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

    public LoginController(OAuth2AuthorizedClientService auth2AuthorizedClientService) {
        this.auth2AuthorizedClientService = auth2AuthorizedClientService;
    }

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

    @RequestMapping("/*")
    public String getUserInfo(Principal user) {

            return user.toString();
    }

//    private StringBuffer getOauth2LoginInfo(Principal user){
//        StringBuffer protectedInfo = new StringBuffer();
//        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
//
//        OAuth2AuthorizedClient authorizedClient = this.auth2AuthorizedClientService
//                .loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
//
//        if(authToken.isAuthenticated()) {
//        Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();
//
//        String userToken = authorizedClient.getAccessToken().getTokenValue();
//        protectedInfo.append("Welcome, " + userAttributes.get("name")+"<br><br>");
//        protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");
//        protectedInfo.append("Access Token: " + userToken+"<br><br>");
//    }
//   else {
//        protectedInfo.append("NA");
//    }
//
//        return protectedInfo;
//    }
//
//    private StringBuffer getUsernamePasswordLoginInfo(Principal user) {
//        StringBuffer usernameInfo = new StringBuffer();
//        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
//
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
