package com.example.myapplication2register.optiuniMeniu;

import android.os.Parcel;
import android.os.Parcelable;

public class Persoana implements Parcelable {
    private String nume;
    private float greutate;
    private float inaltime;

    public Persoana(String nume, float greutate, float inaltime) {
        this.nume = nume;
        this.greutate = greutate;
        this.inaltime = inaltime;
    }

    public String getNume() {
        return nume;
    }

    public float getGreutate() {
        return greutate;
    }

    public float getInaltime() {
        return inaltime;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setGreutate(float greutate) {
        this.greutate = greutate;
    }

    public void setInaltime(float inaltime) {
        this.inaltime = inaltime;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "nume='" + nume + '\'' +
                ", greutate=" + greutate +
                ", inaltime=" + inaltime +
                '}';
    }


    private Persoana(Parcel source){
        nume=source.readString();
        greutate=source.readFloat();
        inaltime=source.readFloat();
    }

    public static Creator<Persoana> CREATOR = new Creator<Persoana>() {
        @Override
        public Persoana createFromParcel(Parcel source) {
            return new Persoana(source);
        }

        @Override
        public Persoana[] newArray(int size) {
            return new Persoana[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeFloat(greutate);
        dest.writeFloat(inaltime);
    }
}

