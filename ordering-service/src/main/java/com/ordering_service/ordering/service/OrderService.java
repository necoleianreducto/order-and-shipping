package com.ordering_service.ordering.service;

import com.ordering_service.ordering.client.ShippingClient;
import com.ordering_service.ordering.dto.request.CreateOrderRequest;
import com.ordering_service.ordering.dto.request.ShippingRequest;
import com.ordering_service.ordering.dto.response.CreateOrderResponse;
import com.ordering_service.ordering.dto.response.SearchOrderCodeResponse;
import com.ordering_service.ordering.mapper.OrderMapper;
import com.ordering_service.ordering.model.Order;
import com.ordering_service.ordering.repository.OrderRepository;
import com.ordering_service.ordering.utils.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ShippingClient shippingClient;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, ShippingClient shippingClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.shippingClient = shippingClient;
    }

    public SearchOrderCodeResponse getOrderDtoByOrderCode(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber);
        return orderMapper.buildSearchOrderResponse(order);
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        Order order = orderMapper.buildCreateOrderRequest(request);
        order.getOrderItems().forEach(item -> item.setOrder(order));

        String trackingNumber = null;

        try {
            ShippingRequest shippingRequest = orderMapper.createShippingRequest(order);
            trackingNumber = shippingClient.createTrackingNumber(shippingRequest);
            order.setTrackingNumber(trackingNumber);
        } catch (Exception ex) {
            log.error("Failed to create tracking number for order: {}", order.getOrderNumber());
            order.setOrderStatus(String.valueOf(OrderStatus.PENDING));
        }

        order.setTrackingNumber(trackingNumber);
        orderRepository.save(order);
        return new CreateOrderResponse(order.getOrderNumber(), order.getOrderStatus(), trackingNumber, String.valueOf(order.getOrderDate()));
    }

//
//    public List<String> updateOrderStatus(UpdateOrderStatusRequestDTO requestDTO) {
//        return new ArrayList<>();
//    }
}
