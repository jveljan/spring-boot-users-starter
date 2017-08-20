package com.example.demo.api;

import com.example.demo.config.security.SecurityUserDetails;
import com.example.demo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/auth/")
public class AuthController {
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public User getUser(@AuthenticationPrincipal SecurityUserDetails currentUser) {
        return currentUser.getUser();
    }
}