package com.example.enterdata.Data;

import java.util.ArrayList;

public class Data {
    private String data;
    private String UserName;
    private double Data;


    public Data(){

    }
    public void generateUser(String s){
        UserName = s.split(":")[0];
        Data = Double.parseDouble(s.split(":")[1]);
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

    public String getdata() {
        return data;
    }

    public void setdata(String data) {
        this.data = data;
    }
}