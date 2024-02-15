package com.example.analytics.model;
public class UserData {
    private String UserName;
    private double Data;

    public UserData() {
    }

    public UserData(String UserName, double Data) {
        this.UserName = UserName;
        this.Data = Data;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public double getData() {
        return Data;
    }

    public void setData(double Data) {
        this.Data = Data;
    }
}