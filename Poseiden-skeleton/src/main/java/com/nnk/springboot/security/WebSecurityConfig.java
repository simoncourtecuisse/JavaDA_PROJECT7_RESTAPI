package com.nnk.springboot.security;
//
//import com.nnk.springboot.security.services.CustomOAuth2UserService;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.security.OAuth.CustomOAuth2UserService;
import com.nnk.springboot.security.OAuth.OAuth2LoginSuccessHandler;
import com.nnk.springboot.security.services.CustomOidcUserService;
import com.nnk.springboot.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;
//
//    @Autowired
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /////////////////////////////////////////////////////////
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    //@Profile("dev")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/", "/user/**", "/css/**", "/js/**").permitAll()
                .antMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .defaultSuccessUrl("/bidList/list")
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
//                  .userInfoEndpoint()
//                  .oidcUserService(customOidcUserService)
                    .and()
                    .successHandler(oAuth2LoginSuccessHandler)
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
        http.logout()
                .logoutUrl("/logout");
    }


    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private CustomOidcUserService customOidcUserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;


    //    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }


//
//    @Profile("test")
//    @Override
////    @Profile("test")
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/**");
//    }

}
