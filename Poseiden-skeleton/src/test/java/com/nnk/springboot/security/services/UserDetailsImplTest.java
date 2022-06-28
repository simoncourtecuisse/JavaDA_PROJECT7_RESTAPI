package com.nnk.springboot.security.services;

import com.nnk.springboot.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsImplTest {

    @Mock
    private User mockUser;

    private UserDetailsImpl userDetailsImplUnderTest;

    @BeforeEach
    void setUp() {
        userDetailsImplUnderTest = new UserDetailsImpl(mockUser);
    }

    @Test
    void testGetAuthorities() {
        when(mockUser.getRole()).thenReturn("result");

        Collection<? extends GrantedAuthority> result = userDetailsImplUnderTest.getAuthorities();
    }

    @Test
    void testGetPassword() {
        when(mockUser.getPassword()).thenReturn("password");

        String result = userDetailsImplUnderTest.getPassword();
        assertEquals("password", result);
    }

    @Test
    void testGetUsername() {
        when(mockUser.getUsername()).thenReturn("username");

        String result = userDetailsImplUnderTest.getUsername();
        assertEquals("username", result);
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(userDetailsImplUnderTest.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(userDetailsImplUnderTest.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(userDetailsImplUnderTest.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(userDetailsImplUnderTest.isEnabled());
    }
}
