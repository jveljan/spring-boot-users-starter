package com.example.demo.api.admin;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreateUserReq;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UsersController {
  UserService userService;

  @GetMapping
  public Page<User> list(Pageable pageable) {
    return userService.listUsers(pageable);
  }


  @PostMapping
  public User create(@RequestBody @Valid CreateUserReq req) {
    return userService.createUser(req);
  }

}
