package com.nnk.springboot.security.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImplUnderTest;

    @Test
    void testLoadUserByUsername() {
        Optional<User> user = Optional.of(new User("username", "password", "fullName"));
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        UserDetails result = userDetailsServiceImplUnderTest.loadUserByUsername("username");
    }

//    @Test
//        void testLoadUserByUsername_ThrowsUsernameNotFoundException() {
//        // Setup
//        // Configure UserRepository.findByUsername(...).
//        final Optional<User> user = Optional.of(new User("username", "password", "fullName"));
//        when(mockUserRepository.findByUsername("username1")).thenReturn(user);
//
//        // Run the test
//        assertThrows(UsernameNotFoundException.class,
//                () -> mockUserRepository.findByUsername("username1"));
//    }
}
