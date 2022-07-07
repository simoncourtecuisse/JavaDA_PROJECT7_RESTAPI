package com.nnk.springboot.security.OAuth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private OAuth2User user;

    public CustomOAuth2User(OAuth2User user) {
        this.user = user;
    }

    public CustomOAuth2User() {

    }

    @Override
    public Map<String, Object> getAttributes() {
        return user.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public String getName() {
        return user.getAttribute("name");
    }

    public String getUsername() {
        return user.getAttribute("name");
    }

    public String getLogin() {
        return user.getAttribute("login");
    }

    @Override
    public String toString() {
        return "CustomOAuth2User{" +
                "user=" + user +
                '}';
    }
}
