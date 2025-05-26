package com.shipping_service.shipping.service;

import com.shipping_service.shipping.client.OrderServiceClient;
import com.shipping_service.shipping.dto.request.ShippingRequest;
import com.shipping_service.shipping.dto.request.UpdateOrderStatusRequest;
import com.shipping_service.shipping.dto.request.UpdateShipmentRequest;
import com.shipping_service.shipping.dto.response.SearchShipmentResponse;
import com.shipping_service.shipping.mapper.ShipmentMapper;
import com.shipping_service.shipping.model.Shipment;
import com.shipping_service.shipping.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingService {

    private static final Logger log = LoggerFactory.getLogger(ShippingService.class);

    @Autowired
    private final OrderServiceClient orderServiceClient;
    private final ShipmentRepository repo;
    private final ShipmentMapper shipmentMapper;

    public ShippingService(OrderServiceClient orderServiceClient, ShipmentRepository repo, ShipmentMapper shipmentMapper) {
        this.orderServiceClient = orderServiceClient;
        this.repo = repo;
        this.shipmentMapper = shipmentMapper;
    }

    public SearchShipmentResponse searchShipmentByTrackingNumber(String trackingNumber) {
        Shipment shipments = repo.findByTrackingNumber(trackingNumber);
        return shipmentMapper.buildSearchShipment(shipments);

    }

    public List<SearchShipmentResponse> searchShipmentByStatus(String orderStatus) {
        List<Shipment> shipments = repo.findByOrderStatus(orderStatus);
        return shipments.stream()
                .map(shipmentMapper::buildSearchShipment)
                .collect(Collectors.toList());

    }

    public String createShipmentOrder(ShippingRequest request) {
        Shipment shipment = shipmentMapper.buildShipmentOrder(request);
        shipment.getShipmentItems().forEach(item -> item.setShipment(shipment));
        repo.save(shipment);

        return shipment.getTrackingNumber();
    }

    public void updateShipmentDetails(String trackingNumber, UpdateShipmentRequest request) {

        Shipment shipment = repo.findByTrackingNumber(trackingNumber);
        String orderNumber = shipment.getOrderNumber();
        shipment.setOrderStatus(request.getOrderStatus());
        shipment.setCourier(request.getCourier());
        repo.save(shipment);

        try {
            UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
            updateOrderStatusRequest.setOrderNumber(orderNumber);
            updateOrderStatusRequest.setNewOrderStatus(request.getOrderStatus());
            orderServiceClient.updateOrderStatus(updateOrderStatusRequest);
        } catch (Exception ex) {
            log.error("Failed to update order number: {}", orderNumber);
        }
    }
}

