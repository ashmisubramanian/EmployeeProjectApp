package com.pluralsight.springbootcrudwebapp.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtToPrincipalConverter {
    private static final Logger logger = LoggerFactory.getLogger(JwtToPrincipalConverter.class);
    public UserPrincipal convert(DecodedJWT jwt){
       List<SimpleGrantedAuthority> authorities = extractAuthoritiesFromClaim(jwt);
        logger.info("Authorities extracted from JWT: {}", authorities);
        /*List<String> roles = extractRolesFromClaim(jwt);
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());*/
        //System.out.println("extract authorities: "+extractAuthoritiesFromClaim(jwt));
        return UserPrincipal.builder()
                .userId(Long.valueOf(jwt.getSubject()))
                .password(jwt.getClaim("p").asString())
                .userName(jwt.getClaim("u").asString())
                .authorities(extractAuthoritiesFromClaim(jwt))
                //.authorities(authorities)
                .build();
    }
    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
        var claim=jwt.getClaim("r");
        if (claim.isNull()||claim.isMissing()) return List.of();
        //return claim.asList(SimpleGrantedAuthority.class);
        List<SimpleGrantedAuthority> authorities = claim.asList(String.class)
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return authorities;
        /*return claim.asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/
    }
    /*private List<String> extractRolesFromClaim(DecodedJWT jwt) {
        Claim claim = jwt.getClaim("r");
        if (claim.isNull() || claim.isMissing()) return Collections.emptyList();
        return Arrays.asList(claim.asArray(String.class));
    }*/
}
