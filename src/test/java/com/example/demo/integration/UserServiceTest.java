package com.example.demo.integration;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

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
        User user = userService.createUser("test", "test", Arrays.asList("ADMIN"));
        Assert.assertNotNull(user);
        Assert.assertEquals("test", user.getUsername());
        Assert.assertNotEquals("test", user.getPassword());
        Assert.assertEquals("ADMIN", user.getAuthorities().get(0).getName());
    }
}
