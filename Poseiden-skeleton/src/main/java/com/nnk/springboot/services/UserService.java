package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        LOGGER.info("User's successfully found");
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id: " + id));
    }

//    public User getUserById(Integer id) {
//        Optional<User> user = userRepository.findById(id);
//        if (user.isPresent()) {
//            LOGGER.info("User's successfully found");
//            return user.get();
//        } else {
//            LOGGER.error("Failed to find User");
//            return null;
//        }
//    }

    public void saveUser(User user) {
        LOGGER.info("User's successfully created");
        userRepository.save(user);
    }

    public boolean updateUser(Integer id, User updatedUser) {
        boolean updated = false;
        User refUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id: " + id));
        if (refUser != null) {
            refUser.setFullName(updatedUser.getFullName());
            refUser.setUsername(updatedUser.getUsername());
            refUser.setPassword(updatedUser.getPassword());
            refUser.setRoles(updatedUser.getRoles());
            updated = true;
            LOGGER.info("User's successfully updated");
        } else {
            LOGGER.error("Failed to update User");
        }
        return updated;
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

}
