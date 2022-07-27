package com.example.myapplication2register.utilizator;

import java.io.Serializable;

public class Meniu implements Serializable {
String user;
   String datac;
    private String p1;
    private String p2;
    private String p3;
    private String p4;
    private String p5;

    public Meniu() {
    }

    public Meniu(String user,String date,String p1, String p2, String p3, String p4,String p5) {
        this.user=user;
        this.datac=date;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5=p5;
    }

    public String getDate() {
        return datac;
    }

    public void setZi(String zi) {
        this.datac = zi;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }

    public String getP4() {
        return p4;
    }

    public void setP4(String p4) {
        this.p4 = p4;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }
}
