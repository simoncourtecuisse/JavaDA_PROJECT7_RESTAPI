package com.nnk.springboot.security.services;

import com.nnk.springboot.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The class implements UserDetails and store user information which is later encapsulated into Authentication objects.
 * This allows non-security related user information to be stored in a convenient location.
 *
 * @author SimonC.
 * @version 1.0
 */
public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    /**
     * The method assign authorities depending on the users Role.
     *
     * @return The authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = user.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        if (user.getRole().equals("USER")) {
            authorities.add(new SimpleGrantedAuthority("USER"));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserDetailsImpl{" +
                "user=" + user +
                '}';
    }
}
