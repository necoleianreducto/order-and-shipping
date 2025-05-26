package com.ordering_service.ordering.dto.request;

import com.ordering_service.ordering.dto.OrderItemsDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderRequest {

    private String customerName;
    private String address;
    private BigDecimal totalAmount;
    private String createdBy;
    private String updatedBy;

    private List<OrderItemsDTO> items;
}
