package com.nnk.springboot.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The controller manages the home page.
 *
 * @author SimonC.
 * @version 1.0
 */
@Controller
public class HomeController {

    Logger LOGGER = LogManager.getLogger(HomeController.class);

    /**
     * Homepage.
     *
     * @return The home view.
     */
    @RequestMapping("/")
    public String home(Model model) {
        LOGGER.info("Get the homepage");
        return "home";
    }

    /**
     * Admin homepage.
     *
     * @return Redirect to the bid list view.
     */
    @RequestMapping("/admin/home")
    public String adminHome(Model model) {
        LOGGER.info("Get the admin homepage");
        return "redirect:/bidList/list";
    }
}
