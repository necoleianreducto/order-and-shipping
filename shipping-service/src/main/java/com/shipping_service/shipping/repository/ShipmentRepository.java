package com.shipping_service.shipping.repository;

import com.shipping_service.shipping.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Shipment findByTrackingNumber(String trackingNumber);
    List<Shipment> findByOrderStatus(String orderStatus);
}
