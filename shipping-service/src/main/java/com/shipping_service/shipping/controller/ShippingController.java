package com.shipping_service.shipping.controller;

import com.shipping_service.shipping.dto.request.ShippingRequest;
import com.shipping_service.shipping.dto.response.OrderAndItemsResponseDTO;
import com.shipping_service.shipping.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping("/create-tracking")
    public ResponseEntity<String> createTrackingOrder(@RequestBody ShippingRequest request) {
        String trackingNumber = shippingService.createShipmentOrder(request);
        return (trackingNumber != null) ? ResponseEntity.ok(trackingNumber) : ResponseEntity.notFound().build();
    }
}
