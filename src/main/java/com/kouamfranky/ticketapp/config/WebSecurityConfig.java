package com.kouamfranky.ticketapp.config;

import com.kouamfranky.ticketapp.securities.TokenAuthorizationFilter;
import com.kouamfranky.ticketapp.securities.TokenProvider;
import com.kouamfranky.ticketapp.service.LocalUserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 07:00
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.config
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {
    private final TokenProvider tokenProvider;
    private final LocalUserDetailService localUserDetailService;

    public WebSecurityConfig(TokenProvider tokenProvider, LocalUserDetailService localUserDetailService) {
        this.tokenProvider = tokenProvider;
        this.localUserDetailService = localUserDetailService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(c->c.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.sessionManagement(sessionManager  -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
                (request, response, exception) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                }));
        http.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(
                        "/actuator/health",
                        "/actuator/metrics",
                        "/actuator/metrics/**",
                        "/h2-console/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/login")
                .permitAll()
                .anyRequest().authenticated()
        );

        http.addFilterBefore(new TokenAuthorizationFilter(tokenProvider, localUserDetailService), UsernamePasswordAuthenticationFilter.class); // custom protocol Authorization
        return http.build();
    }

}