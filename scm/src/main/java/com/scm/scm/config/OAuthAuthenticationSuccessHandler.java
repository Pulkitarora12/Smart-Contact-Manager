package com.scm.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm.entities.Providers;
import com.scm.scm.entities.User;
import com.scm.scm.helper.AppConstants;
import com.scm.scm.repositories.PageRepository;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private PageRepository repo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        // Get user details from OAuth2 - using OAuth2User interface instead
        DefaultOAuth2User user  = (DefaultOAuth2User)authentication.getPrincipal();
        
        // logger.info(user.getName());
        
        user.getAttributes().forEach((key, value) -> {
            logger.info("{} : {}", key, value);
        });

        // logger.info(user.getAuthorities().toString());

        //identify the provider
        var oauth = (OAuth2AuthenticationToken)authentication;
        String clientId = oauth.getAuthorizedClientRegistrationId();
        
        logger.info(clientId);

        User user1 = new User();
        user1.setUserId(UUID.randomUUID().toString());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setProviderURL(user.getName());
        user1.setEnabled(true);
        user1.setEmailVerified(true);



        if ("google".equals(clientId)) {

            String email = user.getAttribute("email").toString();
            String name = user.getAttribute("name").toString();
            String picture = user.getAttribute("picture").toString();

            user1.setName(name);
            user1.setEmail(email);
            user1.setProfileLink(picture);
            user1.setPassword("password");
            user1.setProvider(Providers.GOOGLE);
            user1.setProviderURL(user.getName());
            user1.setAbout("This account was created using google");

        } else {

            String name = user.getAttribute("name").toString();
            String email = user.getAttribute("email") != null
                ? user.getAttribute("email").toString()
                : user.getAttribute("login").toString() + "@gmail.com";
            String picture = user.getAttribute("avatar_url").toString();

            user1.setName(name);
            user1.setEmail(email);
            user1.setProfileLink(picture);
            user1.setProvider(Providers.GITHUB);
            user1.setPassword("password");
            user1.setProviderURL(user.getName());
            user1.setAbout("This account was created using github");
            

        }
        
        //fetching data and saving to database

        
        User user3 = repo.findByEmail(user1.getEmail()).orElse(null);

        if (user3 == null) {
            repo.save(user1);
            logger.info("User saved .... " + user1.getEmail());
        } else {
            logger.info("User already present" + user1.getEmail());
        }

        response.sendRedirect("/user/profile");
    }
}