package com.spring.security.config.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class PasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsManager userDetailsManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String inputPassword = String.valueOf(authentication.getCredentials());

        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        if(userDetails != null && passwordEncoder.matches(inputPassword, userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(username, inputPassword, userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Invalid credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
