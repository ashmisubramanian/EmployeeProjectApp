package com.pluralsight.springbootcrudwebapp;

import com.pluralsight.springbootcrudwebapp.security.JwtIssuer;
import com.pluralsight.springbootcrudwebapp.security.JwtToPrincipalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SpringbootCrudWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrudWebAppApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	/*@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/

	@Bean
	public WebClient webClient(){
		return WebClient.builder().build();
	}



}
