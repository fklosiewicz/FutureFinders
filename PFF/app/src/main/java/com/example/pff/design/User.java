package com.example.pff.design;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private static final long serialVersionUID = 7866834523482281791L;
    public String username;
    public String password;
    public ArrayList<ArrayList<Integer>> indicators;
    public ArrayList<ArrayList<Integer>> states;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        indicators = new ArrayList<ArrayList<Integer>>();
        states = new ArrayList<ArrayList<Integer>>();
    }
}