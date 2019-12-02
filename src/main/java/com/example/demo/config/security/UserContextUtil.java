package com.example.demo.config.security;

import com.example.demo.model.User;
import com.example.demo.model.exception.ItemNotFoundException;

public class UserContextUtil {
  private static ThreadLocal<User> userTL = new ThreadLocal<>();

  public static User getUser() {
    User rv = userTL.get();
    if (rv == null) {
      throw new ItemNotFoundException("User not found for current thread." + Thread.currentThread().getName());
    }
    return rv;
  }

  public static void init(final User user) {
    userTL.set(user);
  }
  public static void clear() {
    userTL.remove();
  }
}