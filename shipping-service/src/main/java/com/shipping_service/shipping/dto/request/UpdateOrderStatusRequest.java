package com.shipping_service.shipping.dto.request;

import lombok.Data;

@Data
public class UpdateOrderStatusRequest {

    private String orderNumber;
    private String newOrderStatus;
}
