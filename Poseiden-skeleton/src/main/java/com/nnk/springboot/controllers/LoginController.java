package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The controller manages the login.
 *
 * @author SimonC.
 * @version 1.0
 */
@Controller
//@RequestMapping("/app")
public class LoginController {

    Logger LOGGER = LogManager.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * @return Model data and view in a single return value
     */
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        LOGGER.info("Get the login page");
        return mav;
    }

    /**
     * @return Model data and view for the app secure in a single return value
     */
    @GetMapping("/app-secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        LOGGER.info("Get the admin page");
        return mav;
    }

    /**
     * @return Model data and view for errors in a single return value
     */
    @GetMapping("/app-error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        LOGGER.info("Get the error page");
        return mav;
    }
}
