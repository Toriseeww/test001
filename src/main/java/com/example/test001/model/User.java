package com.example.test001.model;

import lombok.Data;

@Data
public class User {
    private String sid;

    private String username;

    private String password;

    private String permissions;
}