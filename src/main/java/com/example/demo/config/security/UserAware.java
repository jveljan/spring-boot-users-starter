package com.example.demo.config.security;

import com.example.demo.model.User;
import lombok.SneakyThrows;

public interface UserAware {

  class UserAwareUserHolder {

    User user;

    public UserAwareUserHolder(User user) {
      this.user = user;
    }

    public boolean isAnonymous() {
      return user == null;
    }

    public User get() {
      if (isAnonymous()) {
        throw new IllegalStateException("User not found");
      }
      return user;
    }

    public boolean hasAuthority(String role) {
      return !isAnonymous() && user.getAuthorities().stream().anyMatch(a -> a.getName().equals(role));
    }

    @SneakyThrows
    public void requireSameAs(User owner) {
      if (!user.getUsername().equals(owner.getUsername())) {
        throw new IllegalAccessException("Illegal access from user " + user.getUsername() + " on user " + owner.getUsername());
      }
    }

    @SneakyThrows
    public void requireRole(String role) {
      if (!hasAuthority(role)) {
        throw new IllegalAccessException("User does not have authority " + role);
      }
    }
  }

  default UserAwareUserHolder userHolder() {
    return new UserAwareUserHolder(UserContextUtil.getUser());
  }

  default User currentUser() {
    return UserContextUtil.getUser();
  }
}
