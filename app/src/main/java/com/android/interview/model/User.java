package com.android.interview.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String username;
    private String email;
    private String uid;

    public User(){}

    public User(String uid, String username, String email) {
        this.uid = uid;
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
