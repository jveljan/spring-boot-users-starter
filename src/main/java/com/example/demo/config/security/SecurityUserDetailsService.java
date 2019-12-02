package com.example.demo.config.security;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import reactor.core.publisher.Mono;

public class SecurityUserDetailsService implements ReactiveUserDetailsService {

  private UserService userService;

  public SecurityUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u = userService.findUserAllowedToLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
    return new SecurityUserDetails(u);
  }

  @Override
  public Mono<UserDetails> findByUsername(String s) {
    return Mono.just(loadUserByUsername(s));
  }
}