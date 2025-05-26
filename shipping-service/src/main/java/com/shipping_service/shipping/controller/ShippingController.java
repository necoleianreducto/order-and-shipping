package com.shipping_service.shipping.controller;

import com.shipping_service.shipping.dto.request.ShippingRequest;
import com.shipping_service.shipping.dto.request.UpdateShipmentRequest;
import com.shipping_service.shipping.dto.response.SearchShipmentResponse;
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

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<SearchShipmentResponse> searchShipment(@PathVariable String trackingNumber) {
        SearchShipmentResponse shipment = shippingService.searchShipmentByTrackingNumber(trackingNumber);
        return (shipment != null) ? ResponseEntity.ok(shipment) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/status/{orderStatus}")
    public ResponseEntity<List<SearchShipmentResponse>> searchShipmentByStatus(@PathVariable String orderStatus) {
        List<SearchShipmentResponse> shipment = shippingService.searchShipmentByStatus(orderStatus);
        return (shipment != null) ? ResponseEntity.ok(shipment) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{trackingNumber}")
    public ResponseEntity<SearchShipmentResponse> updateShipmentDetails(
            @PathVariable String trackingNumber,
            @RequestBody UpdateShipmentRequest updateRequest) {
        shippingService.updateShipmentDetails(trackingNumber,updateRequest);
        return ResponseEntity.ok().build();
    }
}
