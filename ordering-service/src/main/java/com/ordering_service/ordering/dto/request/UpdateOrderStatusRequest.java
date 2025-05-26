package com.ordering_service.ordering.dto.request;

import lombok.Data;

@Data
public class UpdateOrderStatusRequest {

    private String orderNumber;
    private String newOrderStatus;
}
