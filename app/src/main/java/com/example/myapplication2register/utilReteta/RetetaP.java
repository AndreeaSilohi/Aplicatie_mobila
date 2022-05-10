package com.example.myapplication2register.utilReteta;

import java.io.Serializable;

public class RetetaP implements Serializable {
    public String denumire;

    public RetetaP(String denumire) {
        this.denumire = denumire;
    }
    public RetetaP() {

    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
}
