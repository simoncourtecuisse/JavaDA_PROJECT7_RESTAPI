package com.nnk.springboot.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * The class disables the spring boot security for the test Profile to allow the unit tests.
 * This allows non-security related user information to be stored in a convenient location.
 *
 * @author SimonC.
 * @version 1.0
 */
@Profile(value = {"test"})
@Configuration
@Component("disableSecurityBean")
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class TestsSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }
}
