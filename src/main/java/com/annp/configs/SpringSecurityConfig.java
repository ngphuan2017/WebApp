/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.annp.configs;

import com.annp.handlers.LoginSuccessHandler;
import com.annp.handlers.LogoutSuccessHandler;
import com.annp.handlers.MyAccessDenied;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author phuan
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.annp.controllers",
    "com.annp.repository",
    "com.annp.service",
    "com.annp.handlers",
    "com.annp.validator"
})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private LoginSuccessHandler loginHandler;
    @Autowired
    private LogoutSuccessHandler logoutHandler;
    @Autowired
    private MyAccessDenied accessDenied;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(this.loginHandler)
                .failureHandler((request, response, exception) -> {
                    String errorMessage = "Tên đăng nhập hoặc mật khẩu không đúng";
                    request.setAttribute("error", errorMessage);
                    request.getRequestDispatcher("/login").forward(request, response);
                });
        http.rememberMe()
                .key("uniqueAndSecretKey")
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("remember-me-cookie")
                .tokenValiditySeconds(604800);
        http.logout().addLogoutHandler(this.logoutHandler);

        http.exceptionHandling().accessDeniedHandler(this.accessDenied);
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/me/**").authenticated()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')");

        http.csrf().disable();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dkmug1913",
                        "api_key", "156722768334643",
                        "api_secret", "QwtPQ45_8f6BhyTDrGK0C2qG4yk",
                        "secure", true));
        return cloudinary;
    }
}
