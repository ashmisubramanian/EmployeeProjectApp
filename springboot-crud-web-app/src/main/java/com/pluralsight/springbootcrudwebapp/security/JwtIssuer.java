package com.pluralsight.springbootcrudwebapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties jwtProperties;
    public String issue(Long userId, String userName, String password , List<String> roles){
        System.out.println("Role Names: " + roles);
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("i",userId)
                .withClaim("u",userName)
                .withClaim("p",password)
                .withClaim("r",roles)
                //.withArrayClaim("r", roles.toArray(new String[0]))
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));

    }
}
