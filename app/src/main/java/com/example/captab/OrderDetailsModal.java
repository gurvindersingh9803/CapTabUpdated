package com.example.captab;

public class OrderDetailsModal {

    String orderNumber;
    String medicineName;
    int count;
    String date,orderStatus;


    public OrderDetailsModal(){

    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public OrderDetailsModal(String orderNumber, String medicineName, int count, String date, String orderStatus){

        this.orderNumber = orderNumber;
        this.medicineName = medicineName;
        this.count = count;
        this.date = date;
        this.orderStatus = orderStatus;
    }



    public String getOrderNumber() {


        return orderNumber;
    }

    public int getCount() {
        return count;
    }

    public String getOrderstatus() {
        //Log.i("xxxxxxxxx",orderstatus);
        return orderStatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderStatus = orderStatus;
    }

    public String getDate() {
       // Log.i("ddddddddddddd",date);

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
