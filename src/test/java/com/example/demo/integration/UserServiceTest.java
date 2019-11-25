package com.example.demo.integration;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by joco on 20.08.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

  @Autowired
  UserService userService;

  @Test
  public void testCreateUser() {
    User user = createUser("test");
    assertNotNull(user);
    assertEquals("test", user.getUsername());
    assertNotEquals("test", user.getPassword());
    assertEquals("ADMIN", user.getAuthorities().get(0).getName());
  }

  @Test
  public void testFindUser() {
    String username = "tcu1";
    createUser(username);
    User user = userService.findUser(username).get();
    assertNotNull(user);
  }

  @Test
  public void testDeleteUsers() {
    createUser("u1");
    userService.deleteUser("u1");
    assertFalse(userService.findUser("u1").isPresent());
  }

  private User createUser(String username) {
    return userService.createUser(username, "xxx", Arrays.asList("ADMIN"));
  }
}
