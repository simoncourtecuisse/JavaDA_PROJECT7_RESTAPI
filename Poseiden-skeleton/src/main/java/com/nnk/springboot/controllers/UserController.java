package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

/**
 * The controller provides CRUD operations for the user entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see User
 * @see UserService
 */
@Controller
public class UserController {

    Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * User home.
     *
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        LOGGER.info("All Users retrieved");
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    /**
     * The form for adding a user.
     *
     * @param user The user.
     * @return Path of the requested view.
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        LOGGER.info("Getting the addUser Form");
        return "user/add";
    }

    /**
     * Add a user.
     *
     * @param user   The user must be valid.
     * @param result The result of the validation.
     * @param model  Data for the view.
     * @return Redirect to the user list view if the result is valid. If the result has an error or if the username is already taken, it returns to the add form view.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {

        Optional<User> usernameExists = userRepository.findByUsername(user.getUsername());
        if (usernameExists.isPresent()) {
            LOGGER.error("Username already exists");
            model.addAttribute("existedUsername", "Username already exists");
            return "user/add";
        }

        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            userService.saveUser(user);
            model.addAttribute("users", userService.getAllUsers());
            LOGGER.info("User's successfully created !");
            return "redirect:/user/list";
        }
        LOGGER.error("Failed to create a new User");
        return "user/add";
    }

    /**
     * The form for updating a user.
     *
     * @param id    The user id to update.
     * @param model Data for the view.
     * @return Path of the requested view.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        LOGGER.info("Getting the updateUser Form");
        model.addAttribute("user", userService.getUserById(id));
        return "user/update";
    }

    /**
     * Update a user.
     *
     * @param id     The user id to update.
     * @param user   The user must be valid.
     * @param result The result of the validation.
     * @param model  Data for the view.
     * @return Redirect to the user list view if the result is valid. If the result has an error, it returns to the update form view.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            LOGGER.error("Failed to update User");
            return "user/update";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setId(id);
            boolean updated = userService.updateUser(id, user);
            System.out.println(updated);
            if (updated) {
                LOGGER.info("User's successfully updated !");
                model.addAttribute("users", userService.getAllUsers());
            }
            return "redirect:/user/list";
        }
    }

    /**
     * Delete a user.
     *
     * @param id    The user id to delete.
     * @param model Data for the view.
     * @return Redirect to the user list view.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.deleteUserById(id);
        LOGGER.info("User's successfully deleted !");
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/user/list";
    }
}
