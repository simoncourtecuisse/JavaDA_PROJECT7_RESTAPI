package com.nnk.springboot.controllers;

import com.nnk.springboot.payload.request.LoginRequest;
import com.nnk.springboot.payload.response.JwtResponse;
import com.nnk.springboot.repositories.RoleRepository;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.jwt.JwtUtils;
import com.nnk.springboot.security.services.UserDetailsImpl;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/login")
public class AuthController {

    Logger LOGGER = LogManager.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping
    public String login(Model model) {
        return "login";
    }


    @PostMapping
    public String authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles);
        LOGGER.info("You are logging !");
        return "login";
    }
}

//    @PostMapping("/login")
//    public String authenticateUser(@Valid User user) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//        LOGGER.info("You are logging !");
//        return "login";
//    }
//
//    @PostMapping("/signup")
//    public String registerUser(@Valid User user,  BindingResult result) {
//        if (userRepository.existsByUsername(user.getUsername())) {
//            LOGGER.error("Error: Username is already used!");
//            return "redirect:/login";
//        }
//
//        // Create new user's account
//        User newUser = new User(
//                user.getUsername(),
//                user.getFullName(),
//                encoder.encode(user.getPassword()),
//                user.getRole()
//        );
//        if (!result.hasErrors()) {
//            userService.saveUser(newUser);
//            LOGGER.info("User registered successfully !");
//            return "redirect:/login";
//        }
//        return "signup";
//    }