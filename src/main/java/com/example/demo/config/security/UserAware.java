package com.example.demo.config.security;

import com.example.demo.model.User;

public interface UserAware {
  default User getUser() {
    return UserContextUtil.getUser();
  }
}
