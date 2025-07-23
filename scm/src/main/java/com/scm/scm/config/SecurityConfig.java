package com.scm.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class SecurityConfig {

    //create user and login from java code in memoryservice

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User
            .withDefaultPasswordEncoder()
            .username("admin123")
            .password("admin123")
            .roles("admin")
            .build();

        UserDetails user2 = User
            .withDefaultPasswordEncoder()
            .username("admin124")
            .password("admin124")
            .roles("user")
            .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
