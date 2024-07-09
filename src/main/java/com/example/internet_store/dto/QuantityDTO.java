package com.example.internet_store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.NumberFormat;

public class QuantityDTO {
    @Min(value = 1, message = "Минимальное количество 1")
            @NotNull(message = "Поле не должно быть пустым")
    int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
