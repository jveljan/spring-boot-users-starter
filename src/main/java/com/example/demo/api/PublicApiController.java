package com.example.demo.api;

import com.example.demo.dto.UserRegisterReq;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * Created by joco on 20.08.17.
 */
@RestController
@RequestMapping("/api/pub")
public class PublicApiController {
    private UserService userService;
    @Autowired
    public PublicApiController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(value = "/user-register", method = RequestMethod.POST)
    public void userRegister(@RequestBody @Valid UserRegisterReq req) {
        userService.createUser(req.getUsername(), req.getPassword(), Arrays.asList("API_ACCESS"));
    }
}
