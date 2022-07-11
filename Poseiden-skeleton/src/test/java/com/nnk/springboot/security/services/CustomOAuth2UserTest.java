package com.nnk.springboot.security.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserTest {

    @Mock
    private OAuth2User mockOAuth2User;

    private CustomOAuth2User customOAuth2UserUnderTest;

    @BeforeEach
    void setUp() {
        customOAuth2UserUnderTest = new CustomOAuth2User(mockOAuth2User);
    }

    @Test
    void testGetAttributes() {
        // Setup
        // Configure OAuth2User.getAttributes(...).
        final Map<String, Object> stringObjectMap = Map.ofEntries(Map.entry("value", "value"));
        when(mockOAuth2User.getAttributes()).thenReturn(stringObjectMap);

        // Run the test
        final Map<String, Object> result = customOAuth2UserUnderTest.getAttributes();
    }

    @Test
    void testGetAuthorities() {
        // Setup
        doReturn(List.of()).when(mockOAuth2User).getAuthorities();

        // Run the test
        final Collection<? extends GrantedAuthority> result = customOAuth2UserUnderTest.getAuthorities();
    }

    @Test
    void testGetAuthorities_OAuth2UserReturnsNoItems() {
        // Setup
        doReturn(Collections.emptyList()).when(mockOAuth2User).getAuthorities();

        // Run the test
        final Collection<? extends GrantedAuthority> result = customOAuth2UserUnderTest.getAuthorities();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testGetName() {
        // Setup
        // Configure OAuth2User.getAttributes(...).
        final Map<String, Object> stringObjectMap = Map.ofEntries(Map.entry("value", "value"));
        when(mockOAuth2User.getAttributes()).thenReturn(stringObjectMap);

        when(mockOAuth2User.getAttribute("login")).thenReturn("result");

        // Run the test
        final String result = customOAuth2UserUnderTest.getName();

        // Verify the results
        assertEquals("result", result);
    }

    @Test
    void testGetUsername() {
        // Setup
        when(mockOAuth2User.getAttribute("name")).thenReturn("result");

        // Run the test
        final String result = customOAuth2UserUnderTest.getUsername();

        // Verify the results
        assertEquals("result", result);
    }

    @Test
    void testGetLogin() {
        // Setup
        when(mockOAuth2User.getAttribute("login")).thenReturn("result");

        // Run the test
        final String result = customOAuth2UserUnderTest.getLogin();

        // Verify the results
        assertEquals("result", result);
    }
}
