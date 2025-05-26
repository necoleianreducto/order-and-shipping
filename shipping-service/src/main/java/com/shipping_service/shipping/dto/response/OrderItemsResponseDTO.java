package com.shipping_service.shipping.dto.response;

import lombok.Data;

@Data
public class OrderItemsResponseDTO {

    private String itemName;
    private int quantity;
    private double price;

    public OrderItemsResponseDTO(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }
}
