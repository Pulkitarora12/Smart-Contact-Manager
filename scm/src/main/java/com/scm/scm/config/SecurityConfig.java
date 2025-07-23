package com.scm.scm.config;

// import java.beans.Customizer; // Remove this line
import org.springframework.security.config.Customizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.scm.service.impl.SecurityCustomDetailService;


@Configuration
public class SecurityConfig {

    //create user and login from java code in memoryservice

    // @Bean
    // public UserDetailsService userDetailsService() {

    //     UserDetails user1 = User
    //         .withDefaultPasswordEncoder()
    //         .username("admin123")
    //         .password("admin123")
    //         .roles("admin")
    //         .build();

    //     UserDetails user2 = User
    //         .withDefaultPasswordEncoder()
    //         .username("admin124")
    //         .password("admin124")
    //         .roles("user")
    //         .build();

    //     return new InMemoryUserDetailsManager(user1, user2);
    // }


    @Autowired
    private SecurityCustomDetailService userDetailService;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        //userdetailsService ka object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);

        //password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //configuration for security
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated();
            authorize.requestMatchers("/**").permitAll();
        });

        httpSecurity.formLogin(Customizer.withDefaults()); 
        
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
