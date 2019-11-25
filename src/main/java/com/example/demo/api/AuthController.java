package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.security.SecurityUserDetails;
import com.example.demo.model.User;
import com.example.demo.service.SimpleUserAware;
import reactor.core.publisher.Mono;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  SimpleUserAware simpleUserAware;

  @GetMapping("/info")
  public User getUser(@AuthenticationPrincipal SecurityUserDetails currentUser) {
    return currentUser.getUser();
  }

  @GetMapping("/hi")
  public String sayHi() {
    simpleUserAware.doSometing();
    return "bye";
  }
}