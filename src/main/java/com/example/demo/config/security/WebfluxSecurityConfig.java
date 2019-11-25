package com.example.demo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.example.demo.service.UserService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class WebfluxSecurityConfig {

  @Bean
  public SecurityUserDetailsService userDetailsService(UserService userService) {
    return new SecurityUserDetailsService(userService);
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http
        .authorizeExchange(exchange -> {
          exchange.pathMatchers("/api/pub/**").permitAll();
          exchange.anyExchange().hasAuthority("API_ACCESS");
        })
        .csrf().disable()
        .httpBasic(withDefaults())
        .formLogin().disable();
    return http.build();
  }
}