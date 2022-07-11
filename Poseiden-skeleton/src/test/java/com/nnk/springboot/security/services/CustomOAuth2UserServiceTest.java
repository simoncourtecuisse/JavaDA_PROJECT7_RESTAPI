package com.nnk.springboot.security.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REGISTRATION_ID;

class CustomOAuth2UserServiceTest {

    private CustomOAuth2UserService customOAuth2UserServiceUnderTest;

    @Autowired
    ClientRegistrationRepository registrations;

    private ClientRegistration clientRegistration;
    private OAuth2AccessToken accessToken;

    private Map<String, Object> additionalParameters;

    @BeforeEach
    void setUp() {
        customOAuth2UserServiceUnderTest = new CustomOAuth2UserService();

        this.clientRegistration = ClientRegistration.withRegistrationId("registration-1")
                .clientId("client-1")
                .clientSecret("secret")
                .userNameAttributeName("username")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("https://client.com")
                .scope(new LinkedHashSet<>(Arrays.asList("scope1", "scope2")))
                .authorizationUri("https://provider.com/oauth2/authorization")
                .userInfoUri("https://provider.com/oauth2/username")
                .tokenUri("https://provider.com/oauth2/token")
                .clientName("Client 1")
                .build();
        // @formatter:on
        this.accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "access-token-1234", Instant.now(),
                Instant.now().plusSeconds(60), new LinkedHashSet<>(Arrays.asList("scope1", "scope2")));
        this.additionalParameters = new HashMap<>();
        this.additionalParameters.put("param1", "value1");
        this.additionalParameters.put("param2", "value2");
    }

    @Test
    void testLoadUser() {
        // Setup
        OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(this.clientRegistration, this.accessToken,
                this.additionalParameters);

        assertThat(oAuth2UserRequest.getClientRegistration()).isEqualTo(this.clientRegistration);
        assertThat(oAuth2UserRequest.getAccessToken()).isEqualTo(this.accessToken);
        assertThat(oAuth2UserRequest.getAdditionalParameters()).containsAllEntriesOf(this.additionalParameters);

        // Run the test
        final OAuth2User result = customOAuth2UserServiceUnderTest.loadUser(oAuth2UserRequest);

    }

    private OAuth2AuthenticationToken createToken() {
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList("USER"));
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities, Collections.singletonMap("name", "rob"), "name");
        return new OAuth2AuthenticationToken(oAuth2User, authorities, "login-client");
    }

}
