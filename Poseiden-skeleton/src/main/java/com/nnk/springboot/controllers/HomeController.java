package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	Logger LOGGER = LogManager.getLogger(HomeController.class);

	@RequestMapping("/")
	public String home(Model model)	{
		LOGGER.info("Get the homepage");
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		LOGGER.info("Get the admin homepage");
		return "redirect:/bidList/list";
	}
}
