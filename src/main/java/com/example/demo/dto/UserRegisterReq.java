package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserRegisterReq {
    @NotNull
    private String username;
    private String password;
}
