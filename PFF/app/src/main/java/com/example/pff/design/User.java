package com.example.pff.design;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is the user class.
 *
 * The class is mainly used for instantiating a new (serialized) user, which contains a username, password, as well
 * as the user's own list of previous search results.
 *
 *
 * @author Filip Klosiewicz
 * @author Sofia Ozol
 * @author Max Pandolpho
 * @author Samantha Cheng
 * @author Zining Ou
 */

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