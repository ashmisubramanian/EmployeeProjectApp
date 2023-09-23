package com.pluralsight.springbootcrudwebapp.security;

import com.pluralsight.springbootcrudwebapp.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //noinspection removal
        http

                .csrf().disable()
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests// URL to be secured
                                .requestMatchers("/api/v1/register").permitAll()
                                .requestMatchers("/api/v1/login").permitAll()
                                .anyRequest().authenticated() // Allow all other requests without authentication
                );// Configure form-based login with default settings

        return http.build();
    }
}
