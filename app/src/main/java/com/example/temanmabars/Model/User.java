package com.example.temanmabars.Model;

public class User {
    // Atribute
    private String id_user;
    private String userType;
    private String name;
    private String username;
    private String email;
    private String gender;

    // Constructor
    public User (){
        // Default Constructor
    }

    public User(String id_user){
        this.id_user = id_user;
    }

    public User(String id_user, String userType, String name, String username, String email, String gender) {
        this.id_user = id_user;
        this.userType = userType;
        this.name = name;
        this.username = username;
        this.email = email;
        this.gender = gender;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
