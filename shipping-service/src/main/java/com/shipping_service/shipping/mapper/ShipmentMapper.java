package com.shipping_service.shipping.mapper;

import com.shipping_service.shipping.dto.ShipmentItemsDTO;
import com.shipping_service.shipping.dto.request.ShippingRequest;
import com.shipping_service.shipping.dto.response.SearchShipmentResponse;
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

        Shipment shipment = buildShipment(request);

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

    private Shipment buildShipment(ShippingRequest request) {
        Shipment shipment = new Shipment();
        shipment.setOrderNumber(request.getOrderNumber());
        shipment.setTrackingNumber(generateTrackingNumber());
        shipment.setOrderStatus(request.getOrderStatus());
        shipment.setAddress(request.getAddress());
        shipment.setTotalAmount(request.getTotalAmount());
        shipment.setOrderDate(request.getOrderDate());
        shipment.setCreatedBy(request.getCreatedBy());
        shipment.setUpdatedBy("SHIPMENT");
        return shipment;
    }

    private String generateTrackingNumber() {
        return String.format("%s-%d-%04d",
                "TRN",
                Instant.now().getEpochSecond(),
                sequence.incrementAndGet() % 10000);
    }

    public SearchShipmentResponse buildSearchShipment(Shipment shipment) {
        SearchShipmentResponse response = buildSearchShipmentDTO(shipment);

        List<ShipmentItemsDTO> items = shipment.getShipmentItems().stream()
                .map(this::mapItemToDTO)
                .collect(Collectors.toList());

        response.setItems(items);
        return response;
    }

    private ShipmentItemsDTO mapItemToDTO(ShipmentItem item) {
        ShipmentItemsDTO dto = new ShipmentItemsDTO();
        dto.setProductNumber(item.getProductNumber());
        dto.setProductName(item.getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }

    private static SearchShipmentResponse buildSearchShipmentDTO(Shipment shipment) {
        SearchShipmentResponse searchShipmentResponse = new SearchShipmentResponse();
        searchShipmentResponse.setOrderNumber(shipment.getOrderNumber());
        searchShipmentResponse.setTrackingNumber(shipment.getTrackingNumber());
        searchShipmentResponse.setOrderStatus(shipment.getOrderStatus());
        searchShipmentResponse.setOrderDate(shipment.getOrderDate());
        searchShipmentResponse.setCourier(shipment.getCourier());
        searchShipmentResponse.setAddress(shipment.getAddress());
        return searchShipmentResponse;
    }

}
