package com.example.reclaimportugal;

public class User {

    public String Username;
    public String Email;

    public static User CurrentUser;

    public User (String username, String email){
        Username = username;
        Email = email;
    }

}
