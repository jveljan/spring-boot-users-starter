package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;

import com.example.demo.config.security.SecurityUserDetailsService;
import com.example.demo.service.UserService;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.WWW_AUTHENTICATE;

@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurity {

  @Bean
  public SecurityUserDetailsService userDetailsService(UserService userService) {
    return new SecurityUserDetailsService(userService);
  }

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http
        .authorizeExchange(exchange -> {
          exchange.pathMatchers("/pub/**").permitAll();
          exchange.anyExchange().authenticated();
        })
        .csrf().disable()
        .httpBasic(spec -> {
          spec.securityContextRepository(new WebSessionServerSecurityContextRepository());
          spec.authenticationEntryPoint(this::authEntryPoint);
        })
        .formLogin().disable();
    return http.build();
  }

  private Mono<Void> authEntryPoint(ServerWebExchange exchange, AuthenticationException e) {
    return Mono.fromRunnable(() -> {
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      List<String> requestedWith = exchange.getRequest().getHeaders().get("X-Requested-With");
      if (requestedWith == null || !requestedWith.contains("XMLHttpRequest")) {
        response.getHeaders().set(WWW_AUTHENTICATE, "Basic realm=\"Quup\"");
      }
    });
  }
}