package com.example.demo.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.WebSession;

import com.example.demo.config.security.SecurityUserDetails;
import com.example.demo.model.User;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/auth")
public class AuthController {

  @GetMapping("/user")
  public User getUser(@AuthenticationPrincipal SecurityUserDetails currentUser) {
    return currentUser.getUser();
  }

  @GetMapping("/logout")
  public String logout(WebSession session) {
    session.invalidate();
    return "OK";
  }
}