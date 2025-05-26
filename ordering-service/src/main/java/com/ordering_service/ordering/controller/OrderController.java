package com.ordering_service.ordering.controller;

import com.ordering_service.ordering.dto.request.CreateOrderRequest;
import com.ordering_service.ordering.dto.request.UpdateOrderStatusRequest;
import com.ordering_service.ordering.dto.response.CreateOrderResponse;
import com.ordering_service.ordering.dto.response.SearchOrderCodeResponse;
import com.ordering_service.ordering.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest dto) {
        CreateOrderResponse response = orderService.createOrder(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{orderNumber}")
    public ResponseEntity<SearchOrderCodeResponse> searchOrderNumber(@PathVariable String orderNumber) {
        SearchOrderCodeResponse order = orderService.getOrderDtoByOrderCode(orderNumber);
        return (order != null) ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @PostMapping("/updateOrderStatus")
    public ResponseEntity<Void> searchOrderNumber(@RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(request);
        return ResponseEntity.noContent().build();
    }
}
