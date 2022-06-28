package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
    void testGetAllUsers() {
        // Setup
        // Configure UserRepository.findAll(...).
        final List<User> users = List.of(new User("username", "password", "fullName"));
        when(mockUserRepository.findAll()).thenReturn(users);

        // Run the test
        final List<User> result = userServiceUnderTest.getAllUsers();

        // Verify the results
    }

    @Test
    void testGetAllUsers_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = userServiceUnderTest.getAllUsers();

        // Verify the results
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void testGetUserById() {
        // Setup
        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User("username", "password", "fullName"));
        when(mockUserRepository.findById(0)).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.getUserById(0);

        // Verify the results
    }

    @Test
    void testGetUserById_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final User result = userServiceUnderTest.getUserById(0);

        // Verify the results
    }

    @Test
    void testSaveUser() {
        // Setup
        final User user = new User("username", "password", "fullName");

        // Configure UserRepository.save(...).
        final User user1 = new User("username", "password", "fullName");
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        // Run the test
        userServiceUnderTest.saveUser(user);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        // Setup
        final User updatedUser = new User("username", "password", "fullName");

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User("username", "password", "fullName"));
        when(mockUserRepository.findById(0)).thenReturn(user);

        // Run the test
        final boolean result = userServiceUnderTest.updateUser(0, updatedUser);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testUpdateUser_UserRepositoryReturnsAbsent() {
        // Setup
        final User updatedUser = new User("username", "password", "fullName");
        when(mockUserRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final boolean result = userServiceUnderTest.updateUser(0, updatedUser);

        // Verify the results
        assertTrue(result);
    }

    @Test
    void testDeleteUserById() {
        // Setup
        // Run the test
        userServiceUnderTest.deleteUserById(0);

        // Verify the results
        verify(mockUserRepository).deleteById(0);
    }
}
