package com.ordering_service.ordering.mapper;

import com.ordering_service.ordering.dto.OrderItemsDTO;
import com.ordering_service.ordering.dto.ShipmentItemsDTO;
import com.ordering_service.ordering.dto.request.CreateOrderRequest;
import com.ordering_service.ordering.dto.request.ShippingRequest;
import com.ordering_service.ordering.dto.response.SearchOrderCodeResponse;
import com.ordering_service.ordering.model.Order;
import com.ordering_service.ordering.model.OrderItem;
import com.ordering_service.ordering.utils.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private static final AtomicLong sequence = new AtomicLong(1000);

    public Order buildCreateOrderRequest(CreateOrderRequest dto) {
        Order order = new Order();
        order.setOrderNumber(generateOrderCode());
        order.setOrderStatus(String.valueOf(OrderStatus.PROCESSING));
        order.setAddress(dto.getAddress());
        order.setTotalAmount(dto.getTotalAmount());
        order.setCreatedBy(dto.getCreatedBy());
        order.setUpdatedBy(dto.getUpdatedBy());
        order.setCustomerName(dto.getCustomerName());
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> items = dto.getItems().stream()
                .map(itemDTO -> {
                    OrderItem item = new OrderItem();
                    item.setProductNumber(itemDTO.getProductNumber());
                    item.setProductName(itemDTO.getProductName());
                    item.setQuantity(itemDTO.getQuantity());
                    item.setUnitPrice(itemDTO.getUnitPrice());
                    item.setTotalPrice(itemDTO.getTotalPrice());
                    item.setOrder(order);
                    return item;
                })
                .collect(Collectors.toList());

        order.setOrderItems(items);
        return order;
    }

    public ShippingRequest createShippingRequest(Order order) {
        ShippingRequest shipment = new ShippingRequest();
        shipment.setOrderNumber(order.getOrderNumber());
        shipment.setOrderStatus(order.getOrderStatus());
        shipment.setAddress(order.getAddress());
        shipment.setCreatedBy(order.getCreatedBy());
        shipment.setTotalAmount(order.getTotalAmount());

        List<ShipmentItemsDTO> items = order.getOrderItems().stream()
                .map(itemDTO -> {
                    ShipmentItemsDTO item = new ShipmentItemsDTO();
                    item.setProductNumber(itemDTO.getProductNumber());
                    item.setProductName(itemDTO.getProductName());
                    item.setQuantity(itemDTO.getQuantity());
                    item.setUnitPrice(itemDTO.getUnitPrice());
                    item.setTotalPrice(itemDTO.getTotalPrice());
                    return item;
                })
                .collect(Collectors.toList());

        shipment.setShipmentItems(items);
        return shipment;
    }

    public SearchOrderCodeResponse buildSearchOrderResponse(Order order) {
        SearchOrderCodeResponse searchOrderCodeResponse = new SearchOrderCodeResponse();
        searchOrderCodeResponse.setOrderNumber(order.getOrderNumber());
        searchOrderCodeResponse.setOrderStatus(order.getOrderStatus());
        searchOrderCodeResponse.setTrackingNumber(order.getTrackingNumber());
        searchOrderCodeResponse.setOrderDate(order.getOrderDate());

        List<OrderItemsDTO> orderItems = order.getOrderItems().stream()
                .map(item -> {
                    OrderItemsDTO dto = new OrderItemsDTO();
                    dto.setProductNumber(item.getProductNumber());
                    dto.setProductName(item.getProductName());
                    dto.setQuantity(item.getQuantity());
                    dto.setUnitPrice(item.getUnitPrice());
                    dto.setTotalPrice(item.getTotalPrice());
                    return dto;
                })
                .collect(Collectors.toList());

        searchOrderCodeResponse.setOrderItemsDTOS(orderItems);
        return searchOrderCodeResponse;
    }

    private String generateOrderCode() {
        return String.format("%s-%d-%04d",
                "ORN",
                Instant.now().getEpochSecond(),
                sequence.incrementAndGet() % 10000);
    }
}
