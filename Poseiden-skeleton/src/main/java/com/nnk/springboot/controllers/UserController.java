package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.OAuth.CustomOAuth2User;
import com.nnk.springboot.security.OAuth.UserPrincipal;
import com.nnk.springboot.services.PasswordConstraintValidator;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/list")
    public String home(UserPrincipal user, Model model) {

//        public String home(Model model, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
       // model.addAttribute("username", user.getAttribute("username"));
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }
//    @RequestMapping("/user/list")
//    public String home(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//        return "user/list";
//    }

    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {

        Optional<User> usernameExists = userRepository.findByUsername(user.getUsername());
        if (usernameExists.isPresent()) {
            LOGGER.error("Username already exists");
            model.addAttribute("existedUsername", "Username already exists");
            return "user/add";
        }

        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            userService.saveUser(user);
            model.addAttribute("users", userService.getAllUsers());
            LOGGER.info("User's successfully created !");
            return "redirect:/user/list";
        }
        LOGGER.error("Failed to create a new User");
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setId(id);
            boolean updated = userService.updateUser(id, user);
            System.out.println(updated);
            if (updated) {
                LOGGER.info("User's successfully updated !");
                model.addAttribute("users", userService.getAllUsers());
            }
            return "redirect:/user/list";
        }
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteUserById(id);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/user/list";
    }

//    @Autowired
//    private UserRepository userRepository;
//
//    @RequestMapping("/user/list")
//    public String home(Model model)
//    {
//        model.addAttribute("users", userRepository.findAll());
//        return "user/list";
//    }
//
//    @GetMapping("/user/add")
//    public String addUser(User bid) {
//        return "user/add";
//    }
//
//    @PostMapping("/user/validate")
//    public String validate(@Valid User user, BindingResult result, Model model) {
//        if (!result.hasErrors()) {
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            user.setPassword(encoder.encode(user.getPassword()));
//            userRepository.save(user);
//            model.addAttribute("users", userRepository.findAll());
//            return "redirect:/user/list";
//        }
//        return "user/add";
//    }
//
//    @GetMapping("/user/update/{id}")
//    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
//        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        user.setPassword("");
//        model.addAttribute("user", user);
//        return "user/update";
//    }
//
//    @PostMapping("/user/update/{id}")
//    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
//                             BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "user/update";
//        }
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        user.setPassword(encoder.encode(user.getPassword()));
//        user.setId(id);
//        userRepository.save(user);
//        model.addAttribute("users", userRepository.findAll());
//        return "redirect:/user/list";
//    }
//
//    @GetMapping("/user/delete/{id}")
//    public String deleteUser(@PathVariable("id") Integer id, Model model) {
//        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        userRepository.delete(user);
//        model.addAttribute("users", userRepository.findAll());
//        return "redirect:/user/list";
//    }
}
