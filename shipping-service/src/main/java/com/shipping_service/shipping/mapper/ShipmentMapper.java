package com.shipping_service.shipping.mapper;

import com.shipping_service.shipping.dto.request.ShippingRequest;
import com.shipping_service.shipping.model.Shipment;
import com.shipping_service.shipping.model.ShipmentItem;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class ShipmentMapper {

    private static final AtomicLong sequence = new AtomicLong(1000);

    public Shipment buildShipmentOrder(ShippingRequest request) {

        Shipment shipment = new Shipment();
        shipment.setOrderNumber(request.getOrderNumber());
        shipment.setTrackingNumber(generateTrackingNumber());
        shipment.setOrderStatus(request.getOrderStatus());
        shipment.setAddress(request.getAddress());
        shipment.setTotalAmount(request.getTotalAmount());
        shipment.setOrderDate(request.getOrderDate());
        shipment.setCreatedBy(request.getCreatedBy());
        shipment.setUpdatedBy("SHIPMENT");

        List<ShipmentItem> items = request.getShipmentItems().stream()
                .map(itemDTO -> {
                    ShipmentItem item = new ShipmentItem();
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

    private String generateTrackingNumber() {
        return String.format("%s-%d-%04d",
                "TRN",
                Instant.now().getEpochSecond(),
                sequence.incrementAndGet() % 10000);
    }
}
