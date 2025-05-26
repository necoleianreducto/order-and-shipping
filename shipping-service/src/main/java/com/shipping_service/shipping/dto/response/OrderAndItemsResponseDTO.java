package com.shipping_service.shipping.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderAndItemsResponseDTO {

    private String orderCode;
    private String customerName;
    private LocalDateTime orderDate;
    private String orderStatus;
    private String modeOfPayment;
    private String paymentStatus;
    private BigDecimal totalAmount;
    private List<OrderItemsResponseDTO> items;

    public OrderAndItemsResponseDTO() {}
    public OrderAndItemsResponseDTO(String orderCode, String customerName, LocalDateTime orderDate,
                                    String orderStatus, String modeOfPayment, String paymentStatus, BigDecimal totalAmount,
                                    List<OrderItemsResponseDTO> items) {
        this.orderCode = orderCode;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.modeOfPayment = modeOfPayment;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
        this.items = items;
    }
}
