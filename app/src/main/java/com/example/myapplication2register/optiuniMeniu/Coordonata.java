package com.example.myapplication2register.optiuniMeniu;

public class Coordonata {
    private String inaltime;
    private String  greutate;
    private String indice;


    public Coordonata(String inaltime, String greutate) {
        this.inaltime = inaltime;
        this.greutate = greutate;
         //this.indice = indice;
    }

    public Coordonata() {
    }

    public String getInaltime() {
        return inaltime;
    }

    public void setInaltime(String inaltime) {
        this.inaltime = inaltime;
    }

    public String getGreutate() {
        return greutate;
    }

    public void setGreutate(String greutate) {
        this.greutate = greutate;
    }

//    public String getIndice() {
//        return indice;
//    }
//
//    public void setIndice(String indice) {
//        this.indice = indice;
//    }
}
