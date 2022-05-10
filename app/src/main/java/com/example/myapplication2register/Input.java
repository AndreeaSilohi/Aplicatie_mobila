package com.example.myapplication2register;

import java.io.Serializable;

public class Input implements Serializable {
    String user;
    public int x;
    public int  y;


    public Input(String user,int x, int y) {
        this.user=user;
        this.x = x;
        this.y = y;
    }

    public Input() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Input{" +
                "user='" + user + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
