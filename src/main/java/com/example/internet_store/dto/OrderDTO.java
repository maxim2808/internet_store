package com.example.internet_store.dto;

import com.example.internet_store.models.ProductOrder;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    int orderId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    String stringDate;
    double orderPrice;
    String address;
    String telephoneNumber;
    String orderStatus;
    List<ProductOrder> productsInOrder;

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<ProductOrder> getProductsInOrder() {
        return productsInOrder;
    }

    public void setProductsInOrder(List<ProductOrder> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }
}
