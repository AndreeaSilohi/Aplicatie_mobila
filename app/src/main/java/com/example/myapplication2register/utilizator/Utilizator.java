package com.example.myapplication2register.utilizator;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Utilizator implements Serializable {
    String name;
    String eadress;
    String padress;
    String user;
    String pass;
    String id;

    public Utilizator() {
    }

    public Utilizator(String name, String eadress, String user, String pass,String id) {

        this.name = name;
        this.eadress = eadress;
        this.user=user;
        this.pass=pass;
        this.id=id;
    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEadress() {
        return eadress;
    }

    public void setEadress(String eadress) {
        this.eadress = eadress;
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
