package com.nnk.springboot.security.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OAuth2LoginSuccessHandlerTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandlerUnderTest;

    @Test
    void testOnAuthenticationSuccess() throws Exception {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        final HttpServletResponse response = new MockHttpServletResponse();
        final Authentication authentication = mock(Authentication.class);

        // Configure UserRepository.findByUsername(...).
        final Optional<User> optionalUser = Optional.of(new User("username", "password", "fullName"));
        when(mockUserRepository.findByUsername("username")).thenReturn(optionalUser);

        // Run the test
        oAuth2LoginSuccessHandlerUnderTest.onAuthenticationSuccess(request, response, authentication);

        // Verify the results
        verify(mockUserService).saveUserAfterLoginOAuthLoginSuccess("loginName", "displayName");
        verify(mockUserService).updateUserOAuth(new User("username", "password", "fullName"), "displayName");
    }

    @Test
    void testOnAuthenticationSuccess_UserRepositoryReturnsAbsent() throws Exception {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        final HttpServletResponse response = new MockHttpServletResponse();
        final Authentication authentication = null;
        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        // Run the test
        oAuth2LoginSuccessHandlerUnderTest.onAuthenticationSuccess(request, response, authentication);

        // Verify the results
        verify(mockUserService).saveUserAfterLoginOAuthLoginSuccess("loginName", "displayName");
        verify(mockUserService).updateUserOAuth(new User("username", "password", "fullName"), "displayName");
    }

    @Test
    void testOnAuthenticationSuccess_ThrowsIOException() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        final HttpServletResponse response = new MockHttpServletResponse();
        final Authentication authentication = null;

        // Configure UserRepository.findByUsername(...).
        final Optional<User> optionalUser = Optional.of(new User("username", "password", "fullName"));
        when(mockUserRepository.findByUsername("username")).thenReturn(optionalUser);

        // Run the test
        assertThrows(
                IOException.class,
                () -> oAuth2LoginSuccessHandlerUnderTest.onAuthenticationSuccess(request, response, authentication));
        verify(mockUserService).saveUserAfterLoginOAuthLoginSuccess("loginName", "displayName");
        verify(mockUserService).updateUserOAuth(new User("username", "password", "fullName"), "displayName");
    }

    @Test
    void testOnAuthenticationSuccess_ThrowsServletException() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        final HttpServletResponse response = new MockHttpServletResponse();
        final Authentication authentication = null;

        // Configure UserRepository.findByUsername(...).
        final Optional<User> optionalUser = Optional.of(new User("username", "password", "fullName"));
        when(mockUserRepository.findByUsername("username")).thenReturn(optionalUser);

        // Run the test
        assertThrows(ServletException.class,
                () -> oAuth2LoginSuccessHandlerUnderTest.onAuthenticationSuccess(request, response, authentication));
        verify(mockUserService).saveUserAfterLoginOAuthLoginSuccess("loginName", "displayName");
        verify(mockUserService).updateUserOAuth(new User("username", "password", "fullName"), "displayName");
    }
}
