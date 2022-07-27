package com.example.myapplication2register.administrator;

public class RetetaPentruAdmin {

    private String itemName;
    private String itemDescription;
    private String itemImage;
    private String keyR;

    public RetetaPentruAdmin() {
    }

    public RetetaPentruAdmin(String itemName, String itemDescription, String itemImage) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemImage = itemImage;
    }

    public  String getRecipeName(){

        return itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription(){
        return itemDescription;
    }

    public String getItemImage(){
        return itemImage;
    }

    public String getKeyR() {
        return keyR;
    }

    public void setKeyR(String keyR) {
        this.keyR = keyR;
    }
}