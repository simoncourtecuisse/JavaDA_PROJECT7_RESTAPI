package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.services.UserDetailsServiceImpl;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private DataSource dataSource;

    @Test
    public void testGetAllUsers() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        when(mockUserService.getAllUsers()).thenReturn(users);

        this.mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @Test
    public void testAddUser() throws Exception {
        this.mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void testValidateUser() throws Exception {
        User user = new User("username", "Password123_", "fullName");
        user.setRole("ADMIN");
        when(mockUserService.getUserById(1)).thenReturn(user);

        when(mockUserService.saveUser(any(User.class))).thenReturn(user);

        this.mockMvc.perform(post("/user/validate")
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullName", user.getFullName())
                        .param("role", user.getRole()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testValidateUser_UsernameAlreadyExists() throws Exception {
        Optional<User> user = Optional.of(new User("username", "Password123_", "fullName"));
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        User user1 = new User("username", "Password123_", "fullName");
        when(mockUserService.saveUser(any(User.class))).thenReturn(user1);

        when(mockUserService.getAllUsers()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(post("/user/validate")
                        .param("username", "username")
                        .param("password", "Password123_")
                        .param("fullName", "fullName")
                        .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("existedUsername", "Username already exists"))
                .andExpect(view().name("user/add"));



    }

    @Test
    public void testValidateUser_BadRequest() throws Exception {
        User user = new User("username", "Password123_", "fullName");
        user.setRole("ADMIN");
        when(mockUserService.getUserById(1)).thenReturn(user);

        this.mockMvc.perform(post("/user/validate")
                        .param("username", "")
                        .param("password", "")
                        .param("fullName", "")
                        .param("role", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void testShowUpdateFormForUser() throws Exception {
        User user = new User("username", "Password123_", "fullName");
        user.setRole("ADMIN");
        when(mockUserService.getUserById(1)).thenReturn(user);

        this.mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("user", hasProperty("username", is("username"))));

    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User("username", "123", "fullName");
        user.setId(1);
        user.setRole("ADMIN");
        when(mockUserService.getUserById(1)).thenReturn(user);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("123"));

        when(mockUserService.updateUser(1, user)).thenReturn(true);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        when(mockUserService.getAllUsers()).thenReturn(users);

        this.mockMvc.perform(post("/user/update/1")
                        .param("username", "username")
                        .param("password", user.getPassword())
                        .param("fullName", "fullName")
                        .param("role", "ADMIN"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testUpdateUser_BadRequest() throws Exception {
        User user = new User("username", "Password123_", "fullName");
        user.setRole("ADMIN");
        when(mockUserService.getUserById(1)).thenReturn(user);

        this.mockMvc.perform(post("/user/update/1")
                        .param("username", "")
                        .param("password", "")
                        .param("fullName", "")
                        .param("role", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().hasErrors());

    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = new User("username", "Password123_", "fullName");
        user.setRole("ADMIN");
        when(mockUserService.getUserById(1)).thenReturn(user);

        this.mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/list"));

    }


}
