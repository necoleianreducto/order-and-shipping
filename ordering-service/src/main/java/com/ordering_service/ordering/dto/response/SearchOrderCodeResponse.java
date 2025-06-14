package com.ordering_service.ordering.dto.response;

import com.ordering_service.ordering.dto.OrderItemsDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SearchOrderCodeResponse {

    private String orderNumber;
    private String customerName;
    private String orderStatus;
    private String address;
    private String trackingNumber;
    private LocalDateTime orderDate;
    private List<OrderItemsDTO> items;
}
