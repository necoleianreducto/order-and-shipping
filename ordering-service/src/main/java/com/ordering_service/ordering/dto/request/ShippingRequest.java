package com.ordering_service.ordering.dto.request;

import com.ordering_service.ordering.dto.ShipmentItemsDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ShippingRequest {

    private String orderNumber;
    private String orderStatus;
    private String address;
    private BigDecimal totalAmount;
    private LocalDate orderDate;
    private String createdBy;
    private List<ShipmentItemsDTO> shipmentItems;
}
