package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.config.security.UserAware;

@Service
public class SimpleUserAware implements UserAware {

  public void doSometing() {
    System.out.println("doSomething user: " + getUser());
  }
}
