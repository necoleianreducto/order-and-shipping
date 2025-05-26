package com.shipping_service.shipping.dto.request;

import lombok.*;

import java.time.LocalDate;

@Data
public class UpdateShipmentRequest {

    private String orderStatus;
    private String courier;
}
