package com.nnk.springboot.security.services;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * The service obtains the user attributes of the end-user (the resource owner) from the UserInfo Endpoint
 * (by using the access token granted to the client during the authorization flow) and returns an AuthenticatedPrincipal in the form of an OAuth2User.
 *
 * @author SimonC.
 * @version 1.0
 */
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    /**
     * Overrides the loadUser() method which will be called by Spring OAuth2 upon successful authentication, and it returns a new CustomOAuth2User object.
     *
     * @param oAuth2UserRequest Request the OAuth2UserService uses when initiating a request to the UserInfo Endpoint.
     * @return New CustomOAuth2User object.
     * @throws OAuth2AuthenticationException Throws an authentication error.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        System.out.println(new CustomOAuth2User(oAuth2User));
        return new CustomOAuth2User(oAuth2User);
    }
}