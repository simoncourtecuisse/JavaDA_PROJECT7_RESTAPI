package com.nnk.springboot.services;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The service provides methods that get called by the UserController for CRUD operations.
 *
 * @author SimonC.
 * @version 1.0
 * @see UserController
 * @see UserRepository
 */
@Service
@Transactional
public class UserService {

    Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all the Users contained in the database.
     *
     * @return A list of all the users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get a user from its id.
     *
     * @param id The user id to get.
     * @return The user. If the user id is not found, throws invalid user id.
     */
    public User getUserById(Integer id) {
        LOGGER.info("User's successfully found");
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id: " + id));
    }

    /**
     * Get a user from its username.
     *
     * @param username The user username to get.
     * @return The user. If the user username is not found, throws invalid user username.
     */
    public User getUserByUsername(String username) {
        LOGGER.info("User's successfully found");
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid User Username: " + username));
    }

    /**
     * Save a user.
     *
     * @param user The user to save.
     * @return The user saved.
     */
    public User saveUser(User user) {
        LOGGER.info("User's successfully created");
        return userRepository.save(user);
    }

    /**
     * Update a user.
     * If the user id is not found, throws invalid user id.
     *
     * @param id          The user id to update.
     * @param updatedUser The user to update.
     * @return The updated user saved.
     */
    public boolean updateUser(Integer id, User updatedUser) {
        boolean updated = false;
        User refUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id: " + id));
        if (refUser != null) {
            refUser.setFullName(updatedUser.getFullName());
            refUser.setUsername(updatedUser.getUsername());
            refUser.setPassword(updatedUser.getPassword());
            refUser.setRole(updatedUser.getRole());
            updated = true;
            LOGGER.info("User's successfully updated");
        } else {
            LOGGER.error("Failed to update User");
        }
        return updated;
    }

    /**
     * Delete a user.
     *
     * @param id The user id to delete.
     */
    public void deleteUserById(Integer id) {
        LOGGER.info("User's successfully deleted");
        userRepository.deleteById(id);
    }

    /**
     * Save a user after login with OAuth.
     * The password and the role are saved automatically.
     *
     * @param loginName   The username for the user to save.
     * @param displayName The fullName for the user to save.
     */
    public void saveUserAfterLoginOAuthLoginSuccess(String loginName, String displayName) {
        User user = new User();
        user.setUsername(loginName);
        user.setFullName(displayName);
        user.setPassword("Github123!");
        user.setRole("USER");

        userRepository.save(user);
    }

    /**
     * Update a OAuth user.
     *
     * @param user        The user to update.
     * @param displayName The fullName for the user to update.
     */
    public void updateUserOAuth(User user, String displayName) {
        user.setFullName(displayName);
        userRepository.save(user);
    }
}
