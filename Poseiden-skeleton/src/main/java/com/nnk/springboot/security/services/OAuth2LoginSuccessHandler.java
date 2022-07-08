package com.nnk.springboot.security.services;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    Logger LOGGER = LogManager.getLogger(OAuth2LoginSuccessHandler.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String loginName = oAuth2User.getLogin();
        String displayName = oAuth2User.getName();
        System.out.println("loginName = " + loginName);

        Optional<User> user = userRepository.findByUsername(loginName);

        if (user.isEmpty()) {
            userService.saveUserAfterLoginOAuthLoginSuccess(loginName, displayName);
            System.out.println("New User");
            LOGGER.info("New User");
        } else {
            userService.updateUserOAuth(user.get(), displayName);
            System.out.println("User already exists");
            LOGGER.error("User already exists");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}