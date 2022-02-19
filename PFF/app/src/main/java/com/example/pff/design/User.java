package com.example.pff.design;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 7866834523482281791L;
    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}