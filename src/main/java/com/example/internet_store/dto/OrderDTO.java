package com.example.internet_store.dto;

import com.example.internet_store.models.ProductOrder;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;

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
    @NotEmpty(message = "адрес не должен быть пустым")
    String address;
    @Pattern(regexp = "\\+\\d{7,15}", message = "Номер должен начиться на '+' и содержать от 7 до 15 цифр")
    @NotEmpty
    String telephoneNumber;
    String orderStatus;
    @NotEmpty(message = "Имя не должно быть пустым")
            @Size(max = 40, message = "Имя не должно содержать больше 40 символов")
    String customerName;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
