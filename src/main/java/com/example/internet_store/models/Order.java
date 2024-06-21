package com.example.internet_store.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
            @Column(name = "order_id")
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderId;
    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date registrationDate;
    @Column(name = "order_price")
    double orderPrice;
    @Column(name = "address")
    String address;
    @Column(name = "telephone_number")
    String telephoneNumber;
    @Column(name = "order_status")
    String orderStatus;
    @OneToMany(mappedBy = "order")
    List<ProductOrder> productsInOrder;
    @Transient
    String stringDate;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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

    public String getStringDate() {
        if (this.registrationDate!=null){
            return this.registrationDate.toString();
        }
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }
}
