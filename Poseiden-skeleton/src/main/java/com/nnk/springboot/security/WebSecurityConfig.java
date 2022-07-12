package com.nnk.springboot.security;

import com.nnk.springboot.security.services.CustomOAuth2UserService;
import com.nnk.springboot.security.services.OAuth2LoginSuccessHandler;
import com.nnk.springboot.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import javax.sql.DataSource;

/**
 * The class sets up our security implementation. It contains configurations for both OAuth2 social login as well as username and password based login.
 *
 * @author SimonC.
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    /**
     * Allows for easily building in memory authentication, LDAP authentication, JDBC based authentication, adding UserDetailsService, and adding AuthenticationProvider's.
     *
     * @param authenticationManagerBuilder Add authentication based upon the custom AuthenticationProvider that is passed in.
     * @throws Exception Exception.
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(authenticationProvider())
                .jdbcAuthentication()
                .dataSource(dataSource);
    }

    /**
     * It allows configuring web based security for specific http requests.
     *
     * @param httpSecurity All the permissions and the rules for the web app security.
     * @throws Exception Exception.
     */
    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
//                .antMatchers("/", "/login", "/error/**", "/css/**").permitAll()
                .antMatchers("/", "/user/**", "/css/**", "/js/**").permitAll()
//                .antMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**").authenticated()
                .antMatchers("/app-secure/**", "/admin/**").hasAnyAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and().successHandler(oAuth2LoginSuccessHandler)
                .and()
                .formLogin()
                .defaultSuccessUrl("/bidList/list")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe().key("uniqueAndSecret")
                .and().exceptionHandling()
                .accessDeniedPage("/app-error");
    }

    /**
     * It provides encoding and decoding of passwords presented in the UserDetails object that is returned from the configured UserDetailsService.
     *
     * @return PasswordEncoder that uses the BCrypt strong hashing function.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * It leverages a UserDetailsService in order to look up the username, password and GrantedAuthority's.
     * It authenticates the user simply by comparing the password submitted in a UsernamePasswordAuthenticationToken against the one loaded by the UserDetailsService.
     *
     * @return The authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
