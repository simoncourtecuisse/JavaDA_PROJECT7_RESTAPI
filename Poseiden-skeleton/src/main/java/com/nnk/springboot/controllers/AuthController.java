package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserDetailsImpl;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    Logger LOGGER = LogManager.getLogger(AuthController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;
//    @Autowired
//    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public String authenticateUser(@Valid User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        LOGGER.info("You are logging !");
        return "login";
    }

    @PostMapping("/signup")
    public String registerUser(@Valid User user,  BindingResult result) {
        if (userRepository.existsByUsername(user.getUsername())) {
            LOGGER.error("Error: Username is already used!");
            return "redirect:/login";
        }

        // Create new user's account
        User newUser = new User(
                user.getUsername(),
                user.getFullName(),
                encoder.encode(user.getPassword()),
                user.getRole()
        );
        if (!result.hasErrors()) {
            userService.saveUser(newUser);
            LOGGER.info("User registered successfully !");
            return "redirect:/login";
        }
        return "signup";
    }
}
