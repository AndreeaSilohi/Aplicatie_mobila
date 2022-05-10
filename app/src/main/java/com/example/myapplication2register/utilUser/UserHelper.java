package com.example.myapplication2register.utilUser;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class UserHelper implements Serializable {
    String name;
    String eadress;
    String padress;
    String user;
    String pass;

    public UserHelper() {
    }

    public UserHelper(String name, String eadress,String user,String pass) {

        this.name = name;
        this.eadress = eadress;
        this.user=user;
        this.pass=pass;
    }


    public String getFname() {
        return name;
    }

    public void setFname(String fname) {
        this.name = fname;
    }


    public String getEadress() {
        return eadress;
    }

    public void setEadress(String eadress) {
        this.eadress = eadress;
    }

    public String getPadress() {
        return padress;
    }

    public void setPadress(String padress) {
        this.padress = padress;
    }


    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @NonNull
    @Override
    public String toString() {
        return "User: " + name + " - " + user;
    }


}
