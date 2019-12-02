package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Table(name = "`USER`")
@Data
public class User {

  @Column
  @Id
  private String username;

  @Column
  @JsonIgnore
  private String password;

  @Column
  private Boolean allowedToLogin = true;

  @Column
  private String email;

  @Column
  private Boolean emailConfirmed = false;


  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Authority> authorities = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(username);
  }

  public boolean hasAuthority(String name) {
    return this.getAuthorities().stream().anyMatch(a -> a.getName().equals(name));
  }

  public void ensureAnyRole(String... roles) throws IllegalAccessException {
    if (!Arrays.stream(roles).anyMatch(r -> hasAuthority(r))) {
      throw new IllegalAccessException("Insufficient privileges, required any of " + Arrays.toString(roles));
    }
  }

  public void ensureIs(User user, String msg) throws IllegalAccessException {
    if (!this.equals(user)) {
      throw new IllegalAccessException(msg);
    }
  }

  public static User flat(User u) {
    User rv = new User();
    rv.setUsername(u.getUsername());
    rv.setEmail(u.getEmail());
    rv.setAuthorities(u.getAuthorities());
    rv.setAllowedToLogin(null);
    rv.setEmailConfirmed(null);
    return rv;
  }
}
