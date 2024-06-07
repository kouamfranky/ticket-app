package com.kouamfranky.ticketapp.securities;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kouamfranky.ticketapp.models.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static java.util.Arrays.stream;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 05/juin/2024 -- 08:23
 *
 * @author name :  Franky Brice on 05/06/2024
 * @author email : kouamfranky@gmail.com /  franky.kouam@iforce5.com
 * Project : @project ticket-app
 * Package : @package com.kouamfranky.ticketapp.securities
 **/
@Component
public class TokenProvider {
    @Value("${app.auth.secret}")
    private String secretKey;
    private final long validityInMilliseconds = System.currentTimeMillis() + 60 * 60 * 1000; // 1 heur

    public String createToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(validityInMilliseconds)) //
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withClaim("principal", user.toMap())
                .sign(getAlgorithm());
    }
    public String refeshToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(validityInMilliseconds))
                .sign(getAlgorithm());
    }


    public String getUsername(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        // recuperer le username
        return decodedJWT.getSubject();
    }
    public Collection<SimpleGrantedAuthority> getAuthorities(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }

    private DecodedJWT getDecodedJWT(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        // verifier le token
        return verifier.verify(token);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey.getBytes());
    }


}
