package com.example.demo.service.init;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateUserReq;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Setup {

  UserService userService;

  public Setup(UserService userService) {
    this.userService = userService;
  }

  void createIfNotExists(String username, String... roles) {
    if (!userService.findUser(username).isPresent()) {
      log.info("Creating {} with roles {}", username, roles);
      userService.createUser(CreateUserReq.of(username, roles));
    }
  }

  @PostConstruct
  void init() {
    createIfNotExists("admin", "SUPER_ADMIN");
  }
}
