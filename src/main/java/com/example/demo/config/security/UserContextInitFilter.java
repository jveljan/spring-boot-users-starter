package com.example.demo.config.security;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.example.demo.model.User;
import reactor.core.publisher.Mono;

@Component
public class UserContextInitFilter implements WebFilter {

  @Override
  public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
    return ReactiveSecurityContextHolder.getContext()
        .map(ctx -> ctx.getAuthentication())
        .map(auth -> {
          SecurityUserDetails userDetails = (SecurityUserDetails) auth.getPrincipal();
          User user = userDetails.getUser();
          UserContextUtil.init(user);
          return Mono.empty();
        }).then(webFilterChain.filter(serverWebExchange));
  }
}
