package com.nnk.springboot.security.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * The class implements OAuth2User and contains the details of the authenticated user.
 *
 * @author SimonC.
 * @version 1.0
 */
public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oAuth2User;

    public CustomOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("login");
    }

    public String getUsername() {
        return oAuth2User.getAttribute("name");
    }

    public String getLogin() {
        return oAuth2User.getAttribute("login");
    }

    @Override
    public String toString() {
        return "CustomOAuth2User{" +
                "user=" + oAuth2User +
                '}';
    }
}