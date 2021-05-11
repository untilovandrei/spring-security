package com.spring.security.config.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class PasswordAuthenticationFilter implements Filter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) request;
        String authorization = r.getHeader("Authorization");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authorization, null);

        Authentication successAuth = authenticationManager.authenticate(auth);

        SecurityContextHolder.getContext().setAuthentication(successAuth);
        chain.doFilter(request, response);
    }
}
