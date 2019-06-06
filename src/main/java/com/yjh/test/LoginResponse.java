package com.yjh.test;

public class LoginResponse {
    private UserModel date;
    private int status;
    private String reasonPhrase;

    public UserModel getDate() {
        return date;
    }

    public void setDate(UserModel date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }
}
