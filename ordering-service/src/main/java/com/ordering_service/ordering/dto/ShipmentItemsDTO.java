package com.ordering_service.ordering.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShipmentItemsDTO {

    private String productNumber;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

}
