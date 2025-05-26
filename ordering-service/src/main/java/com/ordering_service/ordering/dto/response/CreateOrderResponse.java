package com.ordering_service.ordering.dto.response;

import com.ordering_service.ordering.dto.OrderItemsDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderResponse {

    private String orderNumber;
    private String orderStatus;
    private String trackingNumber;
    private String orderDate;

    public CreateOrderResponse(String orderNumber, String orderStatus, String trackingNumber, String orderDate) {
        this.setOrderNumber(orderNumber);
        this.setOrderStatus(orderStatus);
        this.setTrackingNumber(trackingNumber);
        this.setOrderDate(orderDate);
    }
}
