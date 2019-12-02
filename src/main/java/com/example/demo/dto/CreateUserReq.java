package com.example.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserReq {

  @Email
  @NotBlank
  String email;

  String username;

  String password;

  Set<String> roles;

  public static CreateUserReq of(String email) {
    return of(email, null, null);
  }

  public static CreateUserReq of(String email, String password, Set<String> roles) {
    CreateUserReq rv = new CreateUserReq();
    rv.setEmail(email);
    rv.setPassword(password);
    rv.setRoles(roles);
    return rv;
  }

  public static CreateUserReq of(String email, String... roles) {
    Set<String> rolesSet = Arrays.stream(roles).collect(Collectors.toSet());
    return of(email, null, rolesSet);
  }
}
