package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.ERole;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.payload.request.SignupRequest;
import com.nnk.springboot.repositories.RoleRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/signup")
public class SignupController {

    Logger LOGGER = LogManager.getLogger(SignupController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping
    public String signupView(Model model) {
        model.addAttribute(new User());
        return "signup";
    }

    @PostMapping
    public String registerUser(@Valid SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            LOGGER.error("Error: Username is already taken!");
            return "redirect:/login";
        }

        // Create new user's account
        User newUser = new User(
                signupRequest.getUsername(),
                signupRequest.getFullName(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "user" -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }
        newUser.setRoles(roles);
        userService.saveUser(newUser);
        LOGGER.info("User registered successfully !");
        return "redirect:/login";
    }
}
