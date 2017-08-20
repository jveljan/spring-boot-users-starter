package com.example.demo.config.web.interceptors;

import com.example.demo.config.security.SecurityUserDetails;
import com.example.demo.config.web.UserContextUtil;
import com.example.demo.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInjectThreadLocal extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            SecurityUserDetails userDetails = (SecurityUserDetails) auth.getPrincipal();
            User user = userDetails.getUser();
            UserContextUtil.init(user);
        }
        return true;
    }
}