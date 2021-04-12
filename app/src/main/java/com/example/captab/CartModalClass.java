package com.example.captab;

public class CartModalClass {

    String medicineName,orderType;
    String price,quantity,totalPrice;

    public CartModalClass() {

    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CartModalClass(String medicineName, String quantity, String orderType, String price, String totalPrice) {

        this.medicineName = medicineName;
        this.quantity = quantity;
        this.orderType = orderType;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


}
