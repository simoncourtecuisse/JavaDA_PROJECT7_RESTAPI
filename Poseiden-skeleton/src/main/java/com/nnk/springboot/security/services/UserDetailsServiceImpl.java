package com.nnk.springboot.security.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class implements the UserDetailsService interface which loads user-specific data.
 * It is used throughout the framework as a user and is the strategy used by the DaoAuthenticationProvider.
 *
 * @author SimonC.
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * The method locates the user based on the username.
     *
     * @param username The username identifying the user whose data is required.
     * @return A fully populated user record.
     * @throws UsernameNotFoundException Throws an exception if the user could not be found or the user has no GrantedAuthority.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        System.out.println(new UserDetailsImpl(user));
        return new UserDetailsImpl(user);
    }
}
