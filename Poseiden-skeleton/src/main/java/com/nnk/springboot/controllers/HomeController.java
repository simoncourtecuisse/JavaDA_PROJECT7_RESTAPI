package com.nnk.springboot.controllers;

import com.nnk.springboot.security.OAuth.CustomOAuth2User;
import com.nnk.springboot.security.OAuth.UserPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {

	Logger LOGGER = LogManager.getLogger(HomeController.class);

	@RequestMapping("/")
	public String home(CustomOAuth2User principal, Model model)
	{
		System.out.println(principal);
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		OAuth2AuthenticationToken loggedInUser = (OAuth2AuthenticationToken) securityContext.getAuthentication();
//		LOGGER.info("Logged-in user: {}", loggedInUser);
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
