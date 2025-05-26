package com.shipping_service.shipping.service;

import com.shipping_service.shipping.client.OrderServiceClient;
import com.shipping_service.shipping.dto.request.ShippingRequest;
import com.shipping_service.shipping.dto.response.OrderAndItemsResponseDTO;
import com.shipping_service.shipping.mapper.ShipmentMapper;
import com.shipping_service.shipping.model.Shipment;
import com.shipping_service.shipping.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingService {

    @Autowired
    private final OrderServiceClient orderServiceClient;
    private final ShipmentRepository repo;
    private final ShipmentMapper shipmentMapper;

    public ShippingService(OrderServiceClient orderServiceClient, ShipmentRepository repo, ShipmentMapper shipmentMapper) {
        this.orderServiceClient = orderServiceClient;
        this.repo = repo;
        this.shipmentMapper = shipmentMapper;
    }

    public List<OrderAndItemsResponseDTO> processShippingOrders() {
        return orderServiceClient.getForShippingOrders();

    }

    public String createShipmentOrder(ShippingRequest request) {
        Shipment shipment = shipmentMapper.buildShipmentOrder(request);
        shipment.getShipmentItems().forEach(item -> item.setShipment(shipment));
        repo.save(shipment);

        return shipment.getTrackingNumber();
    }
}

