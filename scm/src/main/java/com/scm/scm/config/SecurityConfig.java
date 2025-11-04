package com.scm.scm.config;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.nimbusds.oauth2.sdk.Message;
import com.scm.scm.service.impl.SecurityCustomDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Configuration
public class SecurityConfig {

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

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
        
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailService);

        //password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationFailureHandler customFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException exception)
                                                throws IOException, ServletException {

                if (exception instanceof DisabledException) {
                    // User is disabled (email not verified)
                    response.sendRedirect("/login?error=verification");
                } else {
                    // Other login failures (e.g., bad credentials)
                    response.sendRedirect("/login?error=true");
                }
            }
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //configuration for security
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated();
            authorize.requestMatchers("/**").permitAll();
        });

        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .defaultSuccessUrl("/user/profile", true)
            .failureHandler(customFailureHandler())
            .usernameParameter("email")
            .passwordParameter("password"); 

        }); 

        //authentication failed exception
       
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        //oauth configuration
        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login")
            .successHandler(handler);
        });
        
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
