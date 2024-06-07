package com.kouamfranky.ticketapp.securities;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kouamfranky.ticketapp.service.LocalUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 06/juin/2024 -- 04:51
 *
 * @author name :  Franky Brice on 06/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.config
 **/
public class TokenAuthorizationFilter extends OncePerRequestFilter {
    Logger LOGGER = LogManager.getLogger(TokenAuthorizationFilter.class);

    private final TokenProvider tokenProvider;
    private final LocalUserDetailService localUserDetailService;

    public TokenAuthorizationFilter(TokenProvider tokenProvider, LocalUserDetailService localUserDetailService) {
        this.tokenProvider = tokenProvider;
        this.localUserDetailService = localUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login") || request.getServletPath().equals("/refresh")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    // retirer 'Bearer ' pour avoir le token
                    String token = authorizationHeader.substring("Bearer ".length());

                    // recuperer les infos de l'utilisateur
                    UserDetails userDetails = localUserDetailService.loadUserByUsername(tokenProvider.getUsername(token));

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, tokenProvider.getAuthorities(token));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (JWTVerificationException exception) {
                    response.setStatus(FORBIDDEN.value());
                    LOGGER.error(exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), "Access Denied");
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

}
