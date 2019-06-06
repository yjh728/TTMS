package com.yjh.test.model;

import com.yjh.test.model.ENUM.UserStatus;

public class User {
    private int id;
    private String userName;
    private String password;
    private UserStatus identity;
    private String answer;

    public User() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String account) {
        this.userName = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserStatus getIdentity() {
        return identity;
    }

    public void setIdentity(UserStatus identity) {
        this.identity = identity;
    }
}
