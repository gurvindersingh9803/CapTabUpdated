package com.example.captab;

public class MedicinesModalClass {

    String medName,composition,companyName,category;
    int price;
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public MedicinesModalClass(){

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MedicinesModalClass(String medName, String composition, String medicinesSalt, String category){

        this.medName = medName;
        this.composition = composition;
        this.companyName = medicinesSalt;
        this.category = category;
    }

    public String getMedName() {
        //Log.i("MedNMAE",String.valueOf(getMedName()));
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getPrice() {
        return price;
    }




    public void setPrice(int price) {
        this.price = price;
    }
}
