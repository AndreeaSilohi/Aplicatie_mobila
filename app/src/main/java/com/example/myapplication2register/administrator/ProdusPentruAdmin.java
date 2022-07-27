package com.example.myapplication2register.administrator;

public class ProdusPentruAdmin {
    private String itemName;
    private String itemDescription;
    private String itemImage;
    private String keyP;


    public ProdusPentruAdmin(String itemName, String itemDescription, String itemImage) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemImage = itemImage;
    }


    public ProdusPentruAdmin() {
    }


    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemImage() {
        return itemImage;
    }


    public String getKeyP() {
        return keyP;
    }

    public void setKeyP(String keyP) {
        this.keyP = keyP;
    }
}
