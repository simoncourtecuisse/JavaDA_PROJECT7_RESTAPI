package com.nnk.springboot.security.OAuth;

import com.nnk.springboot.domain.AuthenticationProvider;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    Logger LOGGER = LogManager.getLogger(OAuth2LoginSuccessHandler.class);

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String loginName = oAuth2User.getLogin();
        String displayName = oAuth2User.getName();
        System.out.println(loginName);

        User user = userService.getUserByUsername(loginName);

        if (user == null) {
            userService.saveUserAfterLoginOAuthLoginSuccess(loginName, displayName, AuthenticationProvider.GITHUB);
            System.out.println("New User");
            LOGGER.info("New User");
        } else {
            userService.updateUserOAuth(user, displayName, AuthenticationProvider.GITHUB);
            System.out.println("User already exists");
            LOGGER.error("User already exists");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
