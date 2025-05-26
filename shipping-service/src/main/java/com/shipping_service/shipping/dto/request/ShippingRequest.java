package com.shipping_service.shipping.dto.request;

import com.shipping_service.shipping.dto.ShipmentItemsDTO;
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
