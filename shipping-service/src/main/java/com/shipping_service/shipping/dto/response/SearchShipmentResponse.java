package com.shipping_service.shipping.dto.response;

import com.shipping_service.shipping.dto.ShipmentItemsDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchShipmentResponse {

    private String orderNumber;
    private String orderStatus;
    private String trackingNumber;
    private LocalDate orderDate;
    private String address;
    private String courier;

    private List<ShipmentItemsDTO> items;
}
