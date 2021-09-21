package com.example.agregandooperacionesanuestrabd;

import com.google.firebase.database.DataSnapshot;

public class User {
    public String userId;
    public String username;
    public String email;
    public String password;
    public User() {
// Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public User(String userID, String username, String email, String password) {
        this.userId=userID;
        this.username = username;
        this.email = email;
        this.password=password;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}